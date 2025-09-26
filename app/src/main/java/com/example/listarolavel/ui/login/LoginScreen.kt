package com.example.listarolavel.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ScreenApp(loginViewModel: LoginViewModel = LoginViewModel()) {
    val _UIState by loginViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Cyan
    )
    {
        Card (
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column {
                MyTextField(
                    onValueChange = { loginViewModel.mudarLogin(it) },
                    value = loginViewModel.login,
                    label = _UIState.labelLogin,
                    placeholder = _UIState.labelLogin,
                )

                MyTextField(
                    onValueChange = { loginViewModel.mudarSenha(it) },
                    value = loginViewModel.senha,
                    label = _UIState.labelSenha,
                    placeholder = _UIState.labelSenha,
                )
            }
        }
    }
}

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholder: String = "Placeholder",
    value: String,
    label: String,
    isError: Boolean = false,
    enable: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {Text(label)},
        isError = isError,
        enabled = enable,
        placeholder = { Text(placeholder) }
    )
}