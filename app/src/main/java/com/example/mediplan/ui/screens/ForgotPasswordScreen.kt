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

// Função de extensão para o UserViewModel (assumindo que a lógica real está dentro do ViewModel)
private fun UserViewModel.resetPassword(string: String) {
    // A lógica de redefinição de senha deve ser implementada DENTRO da classe UserViewModel.
    // Esta função de extensão pode ser uma forma de chamá-la, mas a implementação principal
    // (ex: chamada ao Firebase Auth, API, etc.) deve estar no ViewModel.
    // Exemplo: this.initiatePasswordReset(email) // Onde 'this' é o UserViewModel
}

@Composable
fun ForgotPasswordScreen(
    userViewModel: UserViewModel? = null,
    onResetPasswordClick: () -> Unit = {}, // Callback para navegação ou ação após sucesso (opcional)
    onBackToLoginClick: () -> Unit = {}  // Callback para voltar ao ecrã de login
) {
    // Obtém o contexto local.
    val context = LocalContext.current

    // Configuração do ViewModel:
    // Utiliza o ViewModel passado como argumento ou cria uma instância local para previews/casos simples.
    val viewModel = userViewModel ?: remember {
        val database = UserDatabase.getDatabase(context)
        val repository = Repository(database.dao)
        UserViewModel(repository)
    }

    // Estados locais para o campo de email e mensagens de feedback.
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    // Observa o estado da tentativa de redefinição de senha do ViewModel.
    val resetPasswordStateValue by viewModel.resetPasswordState.collectAsState()

    // Efeito lançado para reagir a mudanças no estado de redefinição de senha.
    LaunchedEffect(resetPasswordStateValue) {
        val currentState = resetPasswordStateValue // Captura o estado atual para smart casting.
        when (currentState) {
            is ResetPasswordState.Success -> {
                successMessage = currentState.message // Mostra mensagem de sucesso.
                errorMessage = "" // Limpa mensagem de erro.
                // Opcional: Chamar onResetPasswordClick() para navegar ou executar outra ação.
            }
            is ResetPasswordState.Error -> {
                errorMessage = currentState.message // Mostra mensagem de erro.
                successMessage = "" // Limpa mensagem de sucesso.
            }
            ResetPasswordState.Loading -> {
                // Durante o carregamento, limpa ambas as mensagens.
                // O indicador de progresso será mostrado no UI.
                errorMessage = ""
                successMessage = ""
            }
            ResetPasswordState.Idle -> {
                // No estado inativo, limpa ambas as mensagens.
                errorMessage = ""
                successMessage = ""
            }
        }
    }

    // Estrutura principal do ecrã com um Box.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White) // Fundo branco.
    ) {
        // Coluna para organizar os elementos verticalmente e centralizá-los.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Padding geral.
            horizontalAlignment = Alignment.CenterHorizontally, // Centraliza horizontalmente.
            verticalArrangement = Arrangement.Center // Centraliza verticalmente.
        ) {
            // Imagem do logo da aplicação.
            Image(
                painter = painterResource(id = R.drawable.mediplan_logo),
                contentDescription = "MediPlan Logo",
                modifier = Modifier
                    .size(120.dp) // Tamanho ajustado.
                    .padding(bottom = 32.dp) // Espaçamento inferior.
            )

            // Cartão para agrupar o formulário de recuperação de senha.
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Padding horizontal dentro do cartão.
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Sombra do cartão.
                colors = CardDefaults.cardColors(containerColor = White) // Cor de fundo do cartão.
            ) {
                // Coluna interna do cartão para os elementos do formulário.
                Column(
                    modifier = Modifier
                        .padding(24.dp) // Padding interno do cartão.
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally // Centraliza elementos no cartão.
                ) {
                    // Título do ecrã.
                    Text(
                        text = "Recuperar Senha",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Texto instrutivo.
                    Text(
                        text = "Digite seu email e enviaremos uma nova senha temporária.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center // Texto centralizado.
                    )

                    // Campo de texto para o email.
                    AdaptiveOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.Black) },
                        placeholder = { Text("Digite seu email", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email, // Teclado para email.
                            imeAction = ImeAction.Done // Ação "Done" no teclado.
                        ),
                        singleLine = true, // Campo de uma única linha.
                        textColor = Color.Black
                    )

                    // Exibe mensagem de erro, se houver.
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    // Exibe mensagem de sucesso, se houver.
                    if (successMessage.isNotEmpty()) {
                        Text(
                            text = successMessage,
                            color = LightGreen, // Cor verde para sucesso.
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    // Botão para enviar o pedido de redefinição de senha.
                    GradientButton(
                        text = "Enviar Email",
                        onClick = {
                            if (email.isNotBlank()) { // Verifica se o email não está em branco.
                                viewModel.resetPassword(email) // Chama a função do ViewModel.
                            } else {
                                errorMessage = "Por favor, insira um email válido." // Mensagem de erro se o email estiver vazio.
                            }
                        },
                        modifier = Modifier
                            .width(200.dp) // Largura fixa.
                            .height(45.dp) // Altura reduzida.
                            .padding(bottom = 16.dp),
                        enabled = resetPasswordStateValue !is ResetPasswordState.Loading // Desabilita o botão durante o carregamento.
                    )

                    // Exibe indicador de progresso circular durante o carregamento.
                    if (resetPasswordStateValue is ResetPasswordState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 16.dp),
                            color = LightGreen // Cor do indicador.
                        )
                    }
                }
            }

            // Secção para voltar ao ecrã de login.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp), // Espaçamento superior.
                horizontalArrangement = Arrangement.Center, // Centraliza horizontalmente.
                verticalAlignment = Alignment.CenterVertically // Centraliza verticalmente.
            ) {
                Text(
                    text = "Lembrou da sua senha? ",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                // Botão de texto para navegar de volta ao login.
                TextButton(onClick = onBackToLoginClick) {
                    Text(
                        text = "Voltar ao Login",
                        color = Color.Black, // Pode ser LightGreen para destaque.
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Pré-visualização Composable para o ecrã de recuperação de senha.
@Composable
@Preview(showBackground = true)
fun ForgotPasswordScreenPreview() {
    // Para a pré-visualização, o UserViewModel é criado internamente pelo Composable,
    // o que é útil para visualização isolada.
    ForgotPasswordScreen()
}
