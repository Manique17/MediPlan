package com.example.mediplan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediplan.ui.theme.LightBlue
import com.example.mediplan.ui.theme.LightGreen
import com.example.mediplan.ui.theme.White


/**
 * Um botão com um fundo em gradiente horizontal.
 * O seu aspeto (opacidade) muda se estiver desativado.
 */
@Composable // Indica que esta é uma função de UI
fun GradientButton(
    text: String, // O texto a ser mostrado no botão
    onClick: () -> Unit, // A ação a ser executada quando o botão é clicado
    modifier: Modifier = Modifier, // Modificador para estilizar ou dar layout (opcional)
    enabled: Boolean = true // Se o botão está ativo ou não (opcional, por defeito é ativo)
) {
    // Determina a transparência do conteúdo (gradiente e texto)
    // Se o botão estiver ativo (enabled = true), opacidade total (1f = 100%)
    // Se estiver desativado (enabled = false), meia opacidade (0.5f = 50%)
    val contentAlpha = if (enabled) 1f else 0.5f

    // Usa o componente Button do Material 3 como base
    Button(
        onClick = onClick, // Define a ação do clique
        modifier = modifier, // Aplica quaisquer modificadores passados (ex: tamanho, padding)
        enabled = enabled, // Define se o botão está ativo ou desativado (afeta o clique e a aparência)
        contentPadding = PaddingValues(), // Remove o padding interno padrão do botão para que o Box preencha tudo
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // Cor do contentor do botão fica transparente
            disabledContainerColor = Color.Transparent // Cor do contentor quando desativado também transparente
            // Isto é para que o fundo do Box (com o gradiente) seja sempre visível
        )
    ) {
        // Usa um Box para desenhar o fundo com gradiente e centrar o texto
        Box(
            modifier = Modifier
                .fillMaxSize() // Faz o Box preencher todo o espaço disponível dentro do Button
                .background( // Define o fundo do Box
                    brush = Brush.horizontalGradient( // Cria um gradiente de cores na horizontal
                        colors = listOf( // Lista de cores para o gradiente
                            LightGreen.copy(alpha = contentAlpha), // Verde claro, com a opacidade calculada
                            LightBlue.copy(alpha = contentAlpha)   // Azul claro, com a opacidade calculada
                        )
                    )
                ),
            contentAlignment = Alignment.Center // Alinha o conteúdo do Box (o Text) ao centro
        ) {
            // Mostra o texto do botão
            Text(
                text = text, // O texto a ser exibido
                fontSize = 16.sp, // Tamanho da fonte
                fontWeight = FontWeight.Bold, // Estilo da fonte em negrito
                color = White.copy(alpha = contentAlpha) // Cor do texto branca, com a opacidade calculada
            )
        }
    }
}

// --- Pré-visualizações para o Android Studio ---

/**
 * Pré-visualização do GradientButton no estado ativo (enabled).
 */
@Preview(showBackground = true) // Mostra esta pré-visualização no Android Studio com um fundo
@Composable
fun GradientButtonEnabledPreview() {
    GradientButton(
        text = "ATIVADO", // Texto de exemplo
        onClick = {}, // Ação de clique vazia para a pré-visualização
        modifier = Modifier.padding(16.dp) // Adiciona um padding à volta do botão para melhor visualização
    )
}

/**
 * Pré-visualização do GradientButton no estado desativado (disabled).
 */
@Preview(showBackground = true) // Mostra esta pré-visualização no Android Studio com um fundo
@Composable
fun GradientButtonDisabledPreview() {
    GradientButton(
        text = "DESATIVADO", // Texto de exemplo
        onClick = {}, // Ação de clique vazia
        enabled = false, // Define o botão como desativado para esta pré-visualização
        modifier = Modifier.padding(16.dp) // Adiciona padding para melhor visualização
    )
}

