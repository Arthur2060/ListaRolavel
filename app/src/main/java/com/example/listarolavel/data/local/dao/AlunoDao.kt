package com.example.listarolavel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.listarolavel.data.local.entity.AlunoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {
    @Query("SELECT * FROM alunos WHERE deleted = 0 ORDER BY updatedAt DESC")
    fun listAll(): Flow<List<AlunoEntity>>

    @Query("SELECT * FROM alunos WHERE id = :id AND deleted = 0")
    fun findById(id: String): Flow<List<AlunoEntity?>>

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun getById(id: String): AlunoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cadastrarAluno(aluno: AlunoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cadastrarAlunos(alunos: List<AlunoEntity>)

    @Delete
    suspend fun delete(aluno: AlunoEntity)

    @Query("DELETE FROM alunos WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM alunos WHERE pendingSync = 1")
    suspend fun getPendingSync(): List<AlunoEntity>

    @Query("SELECT id FROM alunos")
    suspend fun getAllIds(): List<String>

    @Query("SELECT * FROM alunos")
    suspend fun getAllI(): List<AlunoEntity>
}