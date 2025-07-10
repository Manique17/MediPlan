package com.example.mediplan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mediplan.ui.theme.White
import com.example.mediplan.ui.theme.LightGreen

@Composable
fun SettingsScreen(
    userName: String,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    var showPasswordDialog by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Configurações",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = LightGreen,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = "Olá, $userName!",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(
            onClick = { showPasswordDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightGreen)
        ) {
            Text("Mudar Palavra-passe", color = Color.White)
        }
        if (showPasswordDialog) {
            AlertDialog(
                onDismissRequest = { showPasswordDialog = false },
                title = { Text("Mudar Palavra-passe") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Nova palavra-passe") },
                            singleLine = true,
                            isError = passwordError.isNotEmpty()
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirmar palavra-passe") },
                            singleLine = true,
                            isError = passwordError.isNotEmpty()
                        )
                        if (passwordError.isNotEmpty()) {
                            Text(passwordError, color = Color.Red, fontSize = 12.sp)
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (newPassword.length < 6) {
                            passwordError = "A palavra-passe deve ter pelo menos 6 caracteres."
                        } else if (newPassword != confirmPassword) {
                            passwordError = "As palavras-passe não coincidem."
                        } else {
                            passwordError = ""
                            showPasswordDialog = false
                            onChangePassword() // Aqui você pode passar a nova senha para a lógica real
                        }
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPasswordDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Terminar Sessão", color = Color.White)
        }
        Button(
            onClick = onDeleteAccount,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Eliminar Conta", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(userName = "João Silva", onChangePassword = {}, onLogout = {}, onDeleteAccount = {})
}
