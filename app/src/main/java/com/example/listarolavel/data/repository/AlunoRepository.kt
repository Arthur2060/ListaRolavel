package com.example.listarolavel.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.listarolavel.data.local.dao.AlunoDao
import com.example.listarolavel.data.local.entity.AlunoEntity
import com.example.listarolavel.data.mapper.toDomain
import com.example.listarolavel.data.mapper.toEntity
import com.example.listarolavel.data.remote.AlunoApi
import com.example.listarolavel.data.worker.AlunoSyncScheduler
import com.example.listarolavel.di.IoDispatcher
import com.example.listarolavel.domain.model.Aluno
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlunoRepository@Inject constructor(
    private val api: AlunoApi,
    private val dao: AlunoDao,
    @IoDispatcher private val io: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    private val jsonMedia = "application/json".toMediaType()

    private fun partJsonDados(dados: Any): RequestBody =
        gson.toJson(dados).toRequestBody(jsonMedia)

    private fun partFromUri(fieldName: String, uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null
        val cr: ContentResolver = context.contentResolver
        val type = cr.getType(uri) ?: "application/octet-stream"
        val fileName = uri.lastPathSegment?.substringAfterLast('/') ?: "arquivo"
        val input = cr.openInputStream(uri) ?: return null
        val tmp = File.createTempFile("up_", "_tmp", context.cacheDir)
        tmp.outputStream().use { out -> input.copyTo(out) }
        val body = tmp.asRequestBody(type.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fieldName, fileName, body)
    }

    private fun partsFromUris(uris: List<Uri>?): List<MultipartBody.Part>? {
        if (uris.isNullOrEmpty()) return null
        return uris.mapNotNull { partFromUri("anexos", it) }
    }

    fun observeUsuarios(): Flow<List<Aluno>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    fun observeUsuario(id: String): Flow<Aluno?> =
        dao.observeById(id).map { it?.toDomain() }

    suspend fun refresh(): Result<Unit> = runCatching {
        val remote = api.list()
        val current = dao.getAllI().associateBy { it.id }

        val merged = remote.map { dto ->
            val old = current[dto.id]
            if (old?.deleted == true) old else dto.toEntity(pending = false)
        }

        dao.cadastrarAlunos(merged)

        val remoteIds = merged.map { it.id }.toSet()
        val toDelete = current.values.filter { it.id !in remoteIds && !it.pendingSync && !it.localOnly }
        toDelete.forEach { dao.deleteById(it.id) }
    }


    suspend fun create(
        nome: String,
        senha: String,
        curso: String,
        notaMedia: Double,
        faltasTotais: Int,
        localOnly: Boolean,
        operationType: String?,
        login: String
    ): Aluno {
        return withContext(io) {

            val tempId = "local-${System.currentTimeMillis()}"
            val localUsuario = AlunoEntity(
                id = tempId,
                nome = nome,
                senha = senha,
                updatedAt = System.currentTimeMillis(),
                pendingSync = true,
                deleted = false,
                curso = curso,
                notaMedia = notaMedia,
                faltasTotais = faltasTotais,
                localOnly = localOnly,
                operationType = operationType,
                login = login
            )

            dao.cadastrarAluno(localUsuario)

            AlunoSyncScheduler.enqueueNow(context)

            localUsuario.toDomain()
        }
    }

    suspend fun update(
        id: String,
        nome: String,
        senha: String?
    ): Aluno {
        return withContext(io) {
            val local = dao.getById(id) ?: throw IllegalArgumentException("Usuário não encontrado")
            val updated = local.copy(
                nome = nome,
                senha = senha ?: local.senha,
                updatedAt = System.currentTimeMillis(),
                pendingSync = true,
                deleted = false,
            )

            dao.cadastrarAluno(updated)
            AlunoSyncScheduler.enqueueNow(context)
            updated.toDomain()
        }
    }


    suspend fun delete(id: String): Result<Unit> = runCatching {
        val local = dao.getById(id) ?: return@runCatching
        dao.cadastrarAluno(
            local.copy(
                deleted = true,
                pendingSync = true,
                updatedAt = System.currentTimeMillis(),
                operationType = "DELETE"
            )
        )
        AlunoSyncScheduler.enqueueNow(context)
    }

    suspend fun sincronizarUsuarios() {
        val pendentes = dao.getPendingSync()

        pendentes.filter { it.operationType == "DELETE" }.forEach { u ->
            try {
                runCatching { api.delete(u.id) }
                dao.deleteById(u.id)
            } catch (e: Exception) {
            }
        }

        pendentes.filter { it.operationType == "CREATE" && !it.deleted }.forEach { u ->
            try {
                val dados = mapOf("nome" to u.nome, "senha" to u.senha)
                val resp = api.create(
                    dadosJson = partJsonDados(dados)
                )
                dao.deleteById(u.id)
                dao.cadastrarAluno(resp.toEntity(pending = false))
            } catch (_: Exception) {
            }
        }

        pendentes.filter { it.operationType == "UPDATE" && !it.deleted }.forEach { u ->
            try {
                val dados = buildMap<String, Any> {
                    put("nome", u.nome)
                    u.senha?.takeIf { it.isNotBlank() }?.let { put("senha", it) }
                }

                val resp = api.update(
                    id = u.id,
                    dadosJson = partJsonDados(dados)
                )

                dao.cadastrarAluno(
                    resp.toEntity(
                        pending = false
                    ).copy(
                        updatedAt = System.currentTimeMillis(),
                        pendingSync = false,
                        localOnly = false,
                        operationType = null
                    )
                )

            } catch (e: Exception) {
                Log.w("UsuarioRepository", "Falha ao sincronizar UPDATE ${u.id}: ${e.message}")
            }
        }

        try {
            val listaApi = api.list()
            val atuais = dao.getAllI().associateBy { it.id }

            val remotos = listaApi.map { dto ->
                val antigo = atuais[dto.id]

                // 1️⃣ se foi deletado localmente, não ressuscita
                if (antigo?.deleted == true) return@map antigo

                val remoto = dto.toEntity(pending = false)

                // 2️⃣ se o local tem pendingSync, ele é mais novo → mantém local
                if (antigo?.pendingSync == true) return@map antigo

                // 3️⃣ se o local tem updatedAt mais recente, mantém local
                if (antigo != null && antigo.updatedAt > remoto.updatedAt) return@map antigo

                // 4️⃣ caso contrário, aceita o remoto (API)
                remoto
            }

            dao.cadastrarAlunos(remotos)

            val idsRemotos = remotos.map { it.id }.toSet()
            val locais = dao.getAllI()
            locais.filter { local ->
                local.id !in idsRemotos && !local.pendingSync && !local.localOnly
            }.forEach { dao.deleteById(it.id) }

        } catch (e: Exception) {
            Log.w("UsuarioRepository", "Sem conexão no pull: ${e.message}")
        }
    }

    @Suppress("unused")
    suspend fun syncAll(): Result<Unit> = runCatching {
        val pendentes = dao.getPendingSync()

        for (e in pendentes) {
            try {
                if (e.localOnly) {
                    val dados = mapOf(
                        "nome" to e.nome,
                        "senha" to e.senha
                    )
                    val resp = api.create(
                        dadosJson = partJsonDados(dados)
                    )
                    dao.deleteById(e.id)
                    dao.cadastrarAluno(resp.toEntity(pending = false))
                } else {
                    val dados = buildMap<String, Any> {
                        put("nome", e.nome)
                        e.senha?.let { put("senha", it) }
                    }
                    val resp = api.update(
                        id = e.id,
                        dadosJson = partJsonDados(dados)
                    )
                    dao.cadastrarAluno(resp.toEntity(pending = false))
                }
            } catch (_: Exception) {
            }
        }
        refresh()
    }

    @Suppress("unused")
    private suspend fun tryPushOne(id: String) {
        val e = dao.getById(id) ?: return

        val dados = buildMap<String, Any> {
            put("nome", e.nome)
            e.senha?.takeIf { it.isNotBlank() }?.let { put("senha", it) }
        }

        val pushed = if (existsRemote(id)) {
            api.update(
                id = id,
                dadosJson = partJsonDados(dados)
            )
        } else {
            api.create(
                dadosJson = partJsonDados(dados)
            )
        }

        dao.cadastrarAluno(pushed.toEntity(pending = false).copy(senha = null))
    }

    private suspend fun existsRemote(id: String): Boolean = runCatching {
        api.get(id); true
    }.getOrDefault(false)

    private fun saveLocalCopy(uri: Uri?): String? {
        if (uri == null) return null
        return try {
            val cr = context.contentResolver
            val input = cr.openInputStream(uri) ?: return null
            val fotosDir = File(context.filesDir, "fotos").apply { mkdirs() }
            val destFile = File(fotosDir, "foto_${System.currentTimeMillis()}.jpg")
            input.use { src -> destFile.outputStream().use { dst -> src.copyTo(dst) } }
            destFile.absolutePath
        } catch (e: Exception) {
            null
        }
    }
}