package com.example.listarolavel.data

class DataResource() {
    fun loadAlunos(): List<Aluno> {
        return listOf(
            Aluno("Arthur", "Mecatrônica/DEV", 9.5, 2, "arthur@gmail.com", "senhaForte"),
            Aluno("Pedro", "DEV", 8.0, 7, "pedro@gmail.com", "senhaForte"),
            Aluno("Diego", "Mecatrônica", 6.5, 200, "diego@gmail.com", "senhaForte"),
        )
    }
}
