package com.example.listarolavel.data.remote.dto

data class AlunoDto (
    val id: String,
    val nome: String,
    val curso: String,
    val notaMedia: Double,
    val faltasTotais: Int,
    val pendingSync: Boolean,
    val updatedAt: Long,
    val localOnly: Boolean,
    val operationType: String?,
    val deleted: Boolean,

    val login: String,
    val senha: String?
)