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
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mediplan.ViewModel.SettingsViewModel

// Importando os temas e cores
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
// Função principal do aplicativo MediPlan
@Composable
fun MediPlanApp() {
    var currentScreen by remember { mutableStateOf("login") }
    var currentUserId by remember { mutableStateOf<String?>(null) }

    val localContext = LocalContext.current // Chamada no escopo @Composable
    val coroutineScope = rememberCoroutineScope() // Para executar operações assíncronas
    
    // Inicializando o ViewModel do usuário e do tema
    val userViewModel = remember {
        val database = UserDatabase.getDatabase(localContext)
        val repository = Repository(database.dao)
        UserViewModel(repository)
    }

    // Inicializando o ViewModel de configurações
    val settingsViewModel = remember {
        SettingsViewModel(localContext)
    }

    // Observando o estado do tema
    MediPlanTheme(darkTheme = settingsViewModel.isDarkMode) {
        when (currentScreen) {
            "login" -> {
                LoginScreen(
                    userViewModel = userViewModel,
                    onLoginClick = { userId ->
                        android.util.Log.d("MainActivity", "Login realizado com sucesso, userId: $userId")
                        Toast.makeText(localContext, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()
                        currentUserId = userId
                        android.util.Log.d("MainActivity", "currentUserId definido como: $currentUserId")
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
                        // Apenas navegar para a tela de login
                        Toast.makeText(localContext, "Conta eliminada com sucesso", Toast.LENGTH_SHORT).show()
                        userViewModel.logout()
                        currentUserId = null
                        currentScreen = "login"
                    },
                    isDarkMode = settingsViewModel.isDarkMode,
                    onThemeChange = { newTheme -> settingsViewModel.setTheme(newTheme) }
                )
            }
        }
    }
}

// Funções de preview para cada tela
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

// Preview para a tela de registro
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

// Preview para a tela de redefinição de senha
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

// Preview para a tela inicial do usuário
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
