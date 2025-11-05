package com.example.listarolavel.domain.model

data class Aluno(
    val id: String,
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