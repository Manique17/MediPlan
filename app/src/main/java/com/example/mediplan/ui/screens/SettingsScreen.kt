package com.example.mediplan.ui.screens

import android.util.Log // Importa o Log para depuração
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
// import androidx.compose.runtime.livedata.observeAsState // Removido se não usares LiveData aqui
// import androidx.compose.ui.platform.LocalContext // Removido se não criares ViewModel local
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mediplan.ui.theme.LightGreen // Considera usar cores do MaterialTheme
// import com.example.mediplan.UserDatabase // Removido
// import com.example.mediplan.ViewModel.Repository // Removido
// import com.example.mediplan.ViewModel.UserViewModel // Removido
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userName: String = "",
    // userId: String = "", // Removido se não for usado diretamente aqui para carregar dados
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onChangePassword: (newPassword: String) -> Unit, // Modificado para passar a nova password
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    // Não cries UserViewModel nem Repository aqui
    // val context = LocalContext.current
    // val database = UserDatabase.getDatabase(context)
    // val repository = Repository(database.dao)
    // val userViewModel = remember { UserViewModel(repository) }

    var showPasswordDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var newPasswordState by remember { mutableStateOf("") } // Renomeado para evitar conflito
    var confirmPasswordState by remember { mutableStateOf("") } // Renomeado
    var passwordErrorState by remember { mutableStateOf("") } // Renomeado

    // // Observa o usuário atual - Removido, pois o ViewModel aqui é local e não o principal
    // val currentUser by userViewModel.currentUser.collectAsState()

    // // Carrega o usuário atual quando o userId muda - Removido
    // LaunchedEffect(userId) {
    //     if (userId.isNotEmpty()) {
    //         val user = repository.getUserById(userId)
    //         if (user != null) {
    //             userViewModel.updateUser(user)
    //         }
    //     }
    // }

    Box(modifier = Modifier.fillMaxSize()) {
        // Conteúdo principal
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Configurações",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            // Usa o userName passado como parâmetro
                            text = if (userName.isNotBlank()) "Olá, $userName!" else "Olá!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Conta",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Button(
                            onClick = {
                                Log.d("SettingsScreen", "Botão Mudar Palavra-passe clicado")
                                // Limpa os campos e abre o diálogo
                                newPasswordState = ""
                                confirmPasswordState = ""
                                passwordErrorState = ""
                                showPasswordDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar senha",
                                    tint = Color.White,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    "Mudar Palavra-passe", 
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Button(
                            onClick = {
                                Log.d("SettingsScreen", "Botão Terminar Sessão clicado")
                                onLogout()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray) // Use MaterialTheme.colorScheme
                        ) {
                            Text("Terminar Sessão", color = Color.White) // Use MaterialTheme.colorScheme
                        }

                        Button(
                            onClick = {
                                Log.d("SettingsScreen", "Botão Eliminar Conta clicado")
                                showDeleteDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Eliminar Conta", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Aparência",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Modo Escuro",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = onThemeChange,
                                thumbContent = {
                                    Icon(
                                        imageVector = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                                        contentDescription = if (isDarkMode) "Modo Escuro Ativado" else "Modo Claro Ativado"
                                    )
                                }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Diálogos - Exibidos em um Box que cobre toda a tela
        if (showPasswordDialog) {
            ChangePasswordDialog(
                newPassword = newPasswordState,
                confirmPassword = confirmPasswordState,
                passwordError = passwordErrorState,
                onNewPasswordChange = { newPasswordState = it; passwordErrorState = "" },
                onConfirmPasswordChange = { confirmPasswordState = it; passwordErrorState = "" },
                onDismiss = {
                    Log.d("SettingsScreen", "ChangePasswordDialog dismissed")
                    showPasswordDialog = false
                },
                onConfirm = {
                    Log.d("SettingsScreen", "ChangePasswordDialog confirmed")
                    if (newPasswordState.length < 6) {
                        passwordErrorState = "A palavra-passe deve ter pelo menos 6 caracteres."
                    } else if (newPasswordState != confirmPasswordState) {
                        passwordErrorState = "As palavras-passe não coincidem."
                    } else {
                        passwordErrorState = ""
                        showPasswordDialog = false
                        // Passa a nova password para o callback
                        try {
                            Log.d("SettingsScreen", "Antes de chamar onChangePassword com senha: ${newPasswordState.take(2)}***")
                            onChangePassword(newPasswordState)
                            Log.d("SettingsScreen", "Depois de chamar onChangePassword")
                        } catch (e: Exception) {
                            Log.e("SettingsScreen", "Erro ao chamar onChangePassword: ${e.message}", e)
                        }
                    }
                }
            )
        }

        if (showDeleteDialog) {
            DeleteAccountDialog(
                onDismiss = {
                    Log.d("SettingsScreen", "DeleteAccountDialog dismissed")
                    showDeleteDialog = false
                },
                onConfirm = {
                    Log.d("SettingsScreen", "DeleteAccountDialog CONFIRMADO - chamando onDeleteAccount callback externo")
                    showDeleteDialog = false
                    // NÃO TENTES ELIMINAR O UTILIZADOR DIRETAMENTE AQUI
                    // currentUser?.let { user ->
                    //     userViewModel.deleteUser(user) // Não uses o userViewModel local para isto
                    // }
                    try {
                        Log.d("SettingsScreen", "Antes de chamar onDeleteAccount")
                        onDeleteAccount() // Apenas chama o callback que veio de fora
                        Log.d("SettingsScreen", "Depois de chamar onDeleteAccount")
                    } catch (e: Exception) {
                        Log.e("SettingsScreen", "Erro ao chamar onDeleteAccount: ${e.message}", e)
                    }
                }
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Configurações",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        // Usa o userName passado como parâmetro
                        text = if (userName.isNotBlank()) "Olá, $userName!" else "Olá!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Conta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = {
                            Log.d("SettingsScreen", "Botão Mudar Palavra-passe clicado")
                            // Limpa os campos e abre o diálogo
                            newPasswordState = ""
                            confirmPasswordState = ""
                            passwordErrorState = ""
                            showPasswordDialog = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar senha",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                "Mudar Palavra-passe", 
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Button(
                        onClick = {
                            Log.d("SettingsScreen", "Botão Terminar Sessão clicado")
                            onLogout()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray) // Use MaterialTheme.colorScheme
                    ) {
                        Text("Terminar Sessão", color = Color.White) // Use MaterialTheme.colorScheme
                    }

                    Button(
                        onClick = {
                            Log.d("SettingsScreen", "Botão Eliminar Conta clicado")
                            showDeleteDialog = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar Conta", color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Aparência",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Modo Escuro",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = onThemeChange,
                            thumbContent = {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                                    contentDescription = if (isDarkMode) "Modo Escuro Ativado" else "Modo Claro Ativado"
                                )
                            }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangePasswordDialog(
    newPassword: String,
    confirmPassword: String,
    passwordError: String,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                "Mudar Palavra-passe", 
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ) 
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Instruções para o usuário
                Text(
                    "Introduza a sua nova palavra-passe e confirme-a.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Campo para nova senha
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = onNewPasswordChange,
                    label = { Text("Nova palavra-passe") },
                    placeholder = { Text("Mínimo 6 caracteres") },
                    singleLine = true,
                    isError = passwordError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                
                // Campo para confirmar senha
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = { Text("Confirmar palavra-passe") },
                    placeholder = { Text("Repita a nova palavra-passe") },
                    singleLine = true,
                    isError = passwordError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                
                // Mensagem de erro, se houver
                if (passwordError.isNotEmpty()) {
                    Text(
                        passwordError, 
                        color = MaterialTheme.colorScheme.error, 
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Confirmar", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteAccountDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Eliminar Conta",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Tem a certeza que deseja eliminar a sua conta?",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "⚠️ Esta ação é irreversível!",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Todos os seus medicamentos e histórico serão permanentemente eliminados.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Sim, Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Settings Screen Light")
@Composable
fun SettingsScreenPreviewLight() {
    MaterialTheme { // Envolve com o MaterialTheme para a Preview usar as cores corretas
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingsScreen(
                userName = "João Silva",
                // userId = "preview-user-id", // Removido da preview se não for usado diretamente
                isDarkMode = false,
                onThemeChange = {},
                onChangePassword = { newPass -> Log.d("Preview", "Mudar password para: $newPass") },
                onLogout = { Log.d("Preview", "Logout") },
                onDeleteAccount = { Log.d("Preview", "Eliminar conta") }
            )
        }
    }
}
