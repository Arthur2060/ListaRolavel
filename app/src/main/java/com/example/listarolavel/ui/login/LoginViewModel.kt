package com.example.listarolavel.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    var login by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set

    fun mudarLogin(novoValue: String) {
        login = novoValue
    }

    fun mudarSenha(novaSenha: String) {
        senha = novaSenha
    }
}