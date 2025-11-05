package com.example.listarolavel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alunos")
data class AlunoEntity (
    @PrimaryKey val id: String,
    val nome: String,
    val curso: String,
    val notaMedia: Double,
    val faltasTotais: Int,
    val pendingSync: Boolean,
    val updatedAt: Long,
    val deleted: Boolean,

    val login: String,
    val senha: String
)