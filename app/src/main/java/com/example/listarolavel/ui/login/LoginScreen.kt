package com.example.listarolavel.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ScreenApp(loginViewModel: LoginViewModel = LoginViewModel()) {
    val uiState by loginViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    MyTextField(
                        onValueChange = { loginViewModel.mudarLogin(it) },
                        value = loginViewModel.login,
                        label = uiState.labelLogin,
                        placeholder = uiState.labelLogin,
                        isError = uiState.errouLoginESenha
                    )

                    MyTextField(
                        onValueChange = { loginViewModel.mudarSenha(it) },
                        value = loginViewModel.senha,
                        label = uiState.labelSenha,
                        placeholder = uiState.labelSenha,
                        isError = uiState.errouLoginESenha
                    )

                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.End
                    ) {
                        LoginButton(
                            onClick = { loginViewModel.logar() }
                        )
                    }


                    if (uiState.loginSucesso) {
                        Text("Acertou!!!!!!!!!!!!!")
                    }
                }
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

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "Login"
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        content = {Text(text)}
    )
}