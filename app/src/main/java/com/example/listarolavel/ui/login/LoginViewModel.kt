package com.example.listarolavel.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    var login by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set

    fun mudarLogin(novoValue: String) {
        login = novoValue
        reset()
    }

    fun mudarSenha(novaSenha: String) {
        senha = novaSenha
        reset()
    }

    fun logar() {
        if (login == "Arthur" && senha == "SenhaForte" ) {
            _uiState.update { currentState ->
                currentState.copy(
                    loginSucesso = true,
                    errouLoginESenha = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    loginSucesso = false,
                    errouLoginESenha = true)
            }
        }
    }

    fun reset() {
        _uiState.update { currentState ->
            currentState.copy(
                errouLoginESenha = false,
                loginSucesso = false)
        }
    }
}