package com.example.listarolavel.data

class DataResource() {
    fun loadAlunos(): List<Aluno> {
        return listOf(
            Aluno("Arthur", "Mecatrônica/DEV", 9.5, 2),
            Aluno("Pedro", "DEV", 8.0, 7),
            Aluno("Diego", "Mecatrônica", 6.5, 200),
            Aluno("Arthur", "Mecatrônica/DEV", 9.5, 2),
            Aluno("Pedro", "DEV", 8.0, 7),
            Aluno("Diego", "Mecatrônica", 6.5, 200),
            Aluno("Arthur", "Mecatrônica/DEV", 9.5, 2),
            Aluno("Pedro", "DEV", 8.0, 7),
            Aluno("Diego", "Mecatrônica", 6.5, 200),
            Aluno("Guilherme", "DEV", 10.0, 1),
        )
    }
}
