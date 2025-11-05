package com.example.listarolavel.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.listarolavel.data.repository.AlunoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class AlunoSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AlunoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        android.util.Log.i("UsuarioSyncWorker", "Executando sincronização...")
        return try {
            repository.sincronizarUsuarios()

            android.util.Log.i("UsuarioSyncWorker", "Sincronização concluída com sucesso.")
            Result.success()
        } catch (e: Exception) {
            android.util.Log.e("UsuarioSyncWorker", "Erro na sincronização", e)
            Result.retry()
        }

    }
}