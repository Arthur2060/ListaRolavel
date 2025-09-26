package com.example.listarolavel.data

data class Aluno(
    val nome: String,
    val curso: String,
    val notaMedia: Double,
    val faltasTotais: Int,

    val login: String,
    val senha: String
)
