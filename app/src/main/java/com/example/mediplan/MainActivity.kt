package com.example.mediplan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.mediplan.ui.screens.ForgotPasswordScreen
import com.example.mediplan.ui.screens.HomeScreen
import com.example.mediplan.ui.screens.LoginScreen
import com.example.mediplan.ui.screens.SignUpScreen
import com.example.mediplan.ui.theme.MediPlanTheme
import com.example.mediplan.UserDatabase
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ViewModel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediPlanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MediPlanApp()
                }
            }
        }
    }
}

@Composable
fun MediPlanApp() {
    val initialDarkTheme = isSystemInDarkTheme()
    var isDarkMode by remember { mutableStateOf(initialDarkTheme) } // (1) ESTADO PRINCIPAL

    // ... outros estados e viewModel ...

    MediPlanTheme(darkTheme = isDarkMode) { // (2) TEMA USA O ESTADO
        val currentScreen = null
        when (currentScreen) {
            // ...
            "home" -> {
                HomeScreen(
                    // ... outros parâmetros ...
                    isDarkMode = isDarkMode,          // (3) ESTADO É PASSADO PARA A HOME
                    onThemeChange = { newThemeState -> // (4) CALLBACK PARA MUDAR O ESTADO
                        isDarkMode = newThemeState
                    }
                )
            }
        }
    }

    var currentScreen by remember { mutableStateOf("login") }
    var currentUserId by remember { mutableStateOf<String?>(null) }

    val localContext = LocalContext.current // Chamada no escopo @Composable
    val userViewModel = remember {
        val database = UserDatabase.getDatabase(localContext) // Usar a variável localContext
        val repository = Repository(database.dao)
        UserViewModel(repository)
    }

    MediPlanTheme(darkTheme = isDarkMode) {
        when (currentScreen) {
            "login" -> {
                LoginScreen(
                    userViewModel = userViewModel,
                    onLoginClick = { userId ->
                        Toast.makeText(localContext, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()
                        currentUserId = userId
                        currentScreen = "home"
                    },
                    onSignUpClick = {
                        currentScreen = "signup"
                    },
                    onForgotPasswordClick = {
                        currentScreen = "forgotpassword"
                    }
                )
            }
            "signup" -> {
                SignUpScreen(
                    userViewModel = userViewModel,
                    onSignUpClick = {
                        Toast.makeText(localContext, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
                        currentScreen = "login"
                    },
                    onLoginClick = {
                        currentScreen = "login"
                    }
                )
            }
            "forgotpassword" -> {
                ForgotPasswordScreen(
                    userViewModel = userViewModel,
                    onResetPasswordClick = {
                        // A lógica de resetar a senha (mostrar mensagem de sucesso/erro)
                        // já está dentro de ForgotPasswordScreen.
                    },
                    onBackToLoginClick = {
                        currentScreen = "login"
                    }
                )
            }
            "home" -> {
                HomeScreen(
                    userId = currentUserId ?: "",
                    onLogout = {
                        Toast.makeText(localContext, "Sessão terminada", Toast.LENGTH_SHORT).show()
                        userViewModel.logout()
                        currentUserId = null
                        currentScreen = "login"
                    },
                    onAccountDeleted = {
                        Toast.makeText(localContext, "Conta eliminada com sucesso", Toast.LENGTH_SHORT).show()
                        userViewModel.logout()
                        currentUserId = null
                        currentScreen = "login"
                    },
                    isDarkMode = isDarkMode,
                    onThemeChange = { isDarkMode = it }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MediPlanTheme {
        val context = LocalContext.current // Chamada no escopo @Composable
        val userViewModel = remember {
            val database = UserDatabase.getDatabase(context)
            val repository = Repository(database.dao)
            UserViewModel(repository)
        }
        LoginScreen(
            userViewModel = userViewModel,
            onLoginClick = {},
            onSignUpClick = {},
            onForgotPasswordClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MediPlanTheme {
        val context = LocalContext.current // Chamada no escopo @Composable
        val userViewModel = remember {
            val database = UserDatabase.getDatabase(context)
            val repository = Repository(database.dao)
            UserViewModel(repository)
        }
        SignUpScreen(
            userViewModel = userViewModel,
            onSignUpClick = {},
            onLoginClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    MediPlanTheme {
        val context = LocalContext.current // Chamada no escopo @Composable
        val userViewModel = remember {
            val database = UserDatabase.getDatabase(context)
            val repository = Repository(database.dao)
            UserViewModel(repository)
        }
        ForgotPasswordScreen(
            userViewModel = userViewModel,
            onResetPasswordClick = {},
            onBackToLoginClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MediPlanTheme {
        HomeScreen(
            userId = "previewUser", // Pode usar um ID de usuário de exemplo para o preview
            onLogout = {},
            onAccountDeleted = {},
            isDarkMode = false,
            onThemeChange = {}
        )
    }
}
