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
import androidx.compose.ui.unit.dp // Adicionado para preview
import androidx.compose.ui.unit.sp
import com.example.mediplan.ui.theme.LightBlue
import com.example.mediplan.ui.theme.LightGreen
import com.example.mediplan.ui.theme.White


@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true // << PARÂMETRO EXISTE E TEM VALOR PADRÃO
) {
    // Determina a opacidade baseada no estado 'enabled'
    val contentAlpha = if (enabled) 1f else 0.5f

    Button(
        onClick = onClick,
        modifier = modifier, // O modifier passado (que pode conter .clip, .height, etc.) é aplicado aqui
        enabled = enabled,   // << PARÂMETRO enabled É USADO AQUI
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent // Para manter o fundo do Box visível quando desabilitado
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize() // O Box preenche o espaço do Button. O tamanho real é definido pelo 'modifier' passado para GradientButton
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LightGreen.copy(alpha = contentAlpha),
                            LightBlue.copy(alpha = contentAlpha)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = White.copy(alpha = contentAlpha)
            )
        }
    }
}

// Previews para testar os estados
@Preview(showBackground = true)
@Composable
fun GradientButtonEnabledPreview() {
    GradientButton(
        text = "ATIVADO",
        onClick = {},
        modifier = Modifier.padding(16.dp) // Adiciona padding para visualização
    )
}

@Preview(showBackground = true)
@Composable
fun GradientButtonDisabledPreview() {
    GradientButton(
        text = "DESATIVADO",
        onClick = {},
        enabled = false,
        modifier = Modifier.padding(16.dp) // Adiciona padding para visualização
    )
}


