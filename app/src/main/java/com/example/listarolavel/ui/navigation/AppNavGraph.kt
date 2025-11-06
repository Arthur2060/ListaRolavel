package com.example.listarolavel.ui.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.listarolavel.ui.feature.login.LoginScreen
import com.example.listarolavel.ui.session.AuthState
import com.example.listarolavel.ui.session.AuthStateViewModel

@Composable
fun AppNavGraph(nav: NavHostController, modifier: Modifier = Modifier) {
    val vm: AuthStateViewModel = hiltViewModel()
    val authState = vm.state.collectAsState().value

    val startDestination = when (authState) {
        is AuthState.Authenticated -> Routes.cadastro
        AuthState.Unauthenticated -> Routes.Login
        AuthState.Loading -> "splash"
    }

    NavHost(
        navController = nav,
        startDestination = startDestination
    ) {
        composable("splash") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        composable(Routes.Login) {
            LoginScreen()
        }

        composable(Routes.cadastro) {
        }

        composable(Routes.Diario) {
        }
    }
}
