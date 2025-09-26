package com.example.listarolavel.ui.login

data class LoginUIState (
    val login: String = "",
    val senha: String = "",
    val senhaCorreta: String = "",

    val acertouLoginESenha: Boolean = false,

    val labelLogin: String = "Login",
    val labelSenha: String = "Senha",

    val errorMensage: String = "Login ou senha incorretos! Tente novamente"
)