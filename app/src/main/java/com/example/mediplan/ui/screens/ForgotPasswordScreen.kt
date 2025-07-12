package com.example.mediplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediplan.R
import com.example.mediplan.UserDatabase
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ViewModel.ResetPasswordState
import com.example.mediplan.ViewModel.UserViewModel
import com.example.mediplan.ui.components.AdaptiveOutlinedTextField
import com.example.mediplan.ui.components.GradientButton
import com.example.mediplan.ui.theme.LightGreen
import com.example.mediplan.ui.theme.White


private fun UserViewModel.resetPassword(string: String) {

}

@Composable
fun ForgotPasswordScreen(
    userViewModel: UserViewModel? = null,
    onResetPasswordClick: () -> Unit = {}, // Pode ser usado para navegação após sucesso, se necessário
    onBackToLoginClick: () -> Unit = {}
) {
    val context = LocalContext.current
    // Se UserViewModel é injetado ou provido de forma mais global, use isso.
    // Caso contrário, a criação aqui é para previews ou casos simples.
    val viewModel = userViewModel ?: remember {
        val database = UserDatabase.getDatabase(context)
        val repository = Repository(database.dao)
        UserViewModel(repository)
    }

    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val resetPasswordStateValue by viewModel.resetPasswordState.collectAsState() // Renomeado para clareza

    // Handle reset password state changes
    LaunchedEffect(resetPasswordStateValue) { // Observar o valor local
        val currentState = resetPasswordStateValue // Capturar o valor atual
        when (currentState) {
            is ResetPasswordState.Success -> {
                successMessage = currentState.message // Agora o smart cast funciona
                errorMessage = ""
                // viewModel.resetPasswordAttemptState() // Opcional: resetar estado no ViewModel após sucesso
                // onResetPasswordClick() // Opcional: navegar ou realizar outra ação
            }
            is ResetPasswordState.Error -> {
                errorMessage = currentState.message // Agora o smart cast funciona
                successMessage = ""
                // viewModel.resetPasswordAttemptState() // Opcional: resetar estado no ViewModel após erro
            }
            ResetPasswordState.Loading -> {
                errorMessage = ""
                successMessage = ""
                // Geralmente não precisa fazer nada aqui, o UI já mostra o indicador
            }
            ResetPasswordState.Idle -> {
                errorMessage = ""
                successMessage = ""
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mediplan_logo), // Assumindo que voc&#234; nomeou o arquivo como "mediplan_logo"
                contentDescription = "MediPlan Logo",
                modifier = Modifier
                    .size(120.dp) // Ajustado para um tamanho menor, j&#225; que a imagem &#233; um &#237;cone
                    .padding(bottom = 32.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Recuperar Senha",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Digite seu email e enviaremos uma nova senha tempor&#225;ria.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    AdaptiveOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.Black) },
                        placeholder = { Text("Digite seu email", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        textColor = Color.Black
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    if (successMessage.isNotEmpty()) {
                        Text(
                            text = successMessage,
                            color = LightGreen,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    GradientButton(
                        text = "Enviar Email",
                        onClick = {
                            if (email.isNotEmpty()) {
                                viewModel.resetPassword(email)
                            } else {
                                errorMessage = "Por favor, insira um email v&#225;lido."
                            }
                        },
                        modifier = Modifier
                            .width(200.dp) // Define uma largura fixa para o bot&#227;o
                            .height(45.dp) // Reduz a altura do bot&#227;o
                            .padding(bottom = 16.dp),
                        enabled = resetPasswordStateValue !is ResetPasswordState.Loading // Desabilitar bot&#227;o durante o carregamento
                    )

                    if (resetPasswordStateValue is ResetPasswordState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 16.dp),
                            color = LightGreen
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lembrou da sua senha? ",
                    color = Color.Black,
                    fontSize = 14.sp
                )

                TextButton(onClick = onBackToLoginClick) {
                    Text(
                        text = "Voltar ao Login",
                        color = Color.Black, // Ou LightGreen para destaque
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ForgotPasswordScreenPreview() {
    // Para o preview, podemos mockar o ViewModel ou usar um estado inicial
    // Aqui, vamos apenas chamar com o UserViewModel nulo, que far&#225; com que
    // o Composable crie sua pr&#243;pria inst&#225;ncia (bom para previews isolados).
    ForgotPasswordScreen()
}