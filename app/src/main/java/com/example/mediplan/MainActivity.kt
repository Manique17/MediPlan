package com.example.mediplan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mediplan.ui.screens.ForgotPasswordScreen
import com.example.mediplan.ui.screens.HomeScreen
import com.example.mediplan.ui.screens.LoginScreen
import com.example.mediplan.ui.screens.SignUpScreen
import com.example.mediplan.ui.theme.MediPlanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediPlanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MediPlanApp(context = this)
                }
            }
        }
    }
}

@Composable
fun MediPlanApp(context: ComponentActivity) {
    var currentScreen by remember { mutableStateOf("login") }
    
    // Callback to handle logout or account deletion
    val onLogout = {
        Toast.makeText(context, "Sessão terminada", Toast.LENGTH_SHORT).show()
        currentScreen = "login"
    }
    
    // Callback to handle account deletion
    val onAccountDeleted = {
        Toast.makeText(context, "Conta eliminada com sucesso", Toast.LENGTH_SHORT).show()
        currentScreen = "login"
    }
    
    when (currentScreen) {
        "login" -> {
            LoginScreen(
                onLoginClick = {
                    // Handle login action
                    Toast.makeText(context, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()
                    // Navigate to home screen with bottom navigation
                    currentScreen = "home"
                },
                onSignUpClick = {
                    // Navigate to sign up screen
                    currentScreen = "signup"
                },
                onForgotPasswordClick = {
                    // Navigate to forgot password screen
                    currentScreen = "forgotpassword"
                }
            )
        }
        "signup" -> {
            SignUpScreen(
                onSignUpClick = {
                    // Handle sign up action
                    Toast.makeText(context, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
                    // Navigate back to login screen
                    currentScreen = "login"
                },
                onLoginClick = {
                    // Navigate back to login screen
                    currentScreen = "login"
                }
            )
        }
        "forgotpassword" -> {
            ForgotPasswordScreen(
                onResetPasswordClick = {
                    // Handle reset password action
                    Toast.makeText(context, "Email de recuperação enviado", Toast.LENGTH_SHORT).show()
                },
                onBackToLoginClick = {
                    // Navigate back to login screen
                    currentScreen = "login"
                }
            )
        }
        "home" -> {
            HomeScreen(
                onLogout = onLogout,
                onAccountDeleted = onAccountDeleted
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MediPlanTheme {
        LoginScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MediPlanTheme {
        SignUpScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    MediPlanTheme {
        ForgotPasswordScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MediPlanTheme {
        HomeScreen(
            onLogout = {},
            onAccountDeleted = {}
        )
    }
}