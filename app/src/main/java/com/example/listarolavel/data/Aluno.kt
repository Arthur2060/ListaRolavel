package com.example.listarolavel.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Aluno(
    @StringRes val nome: Int,
    @StringRes val curso: Int,
    @DrawableRes val imagen: Int
)
