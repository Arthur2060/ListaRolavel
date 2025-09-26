package com.example.listarolavel.ui.login

data class LoginUIState (
    var login: String = "",
    var senha: String = "",
    var senhaCorreta: String = "",

    var errouLoginESenha: Boolean = false,
    var loginSucesso: Boolean = false,

    var labelLogin: String = "Login",
    var labelSenha: String = "Senha",

    val errorMensage: String = "Login ou senha incorretos! Tente novamente"
)