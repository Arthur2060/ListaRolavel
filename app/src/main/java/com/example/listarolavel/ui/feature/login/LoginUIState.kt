package com.example.listarolavel.ui.feature.login

data class LoginUIState (
    val nome: String = "",
    val foto: String = "",
    var login: String = "",
    var senha: String = "",
    var senhaCorreta: String = "",

    var errouLoginESenha: Boolean = false,
    var loginSucesso: Boolean = false,

    var labelNome: String = "Nome",
    var labelFoto: String = "Foto",
    var labelLogin: String = "Login",
    var labelSenha: String = "Senha",

    val errorMensage: String = "Login ou senha incorretos! Tente novamente"
)