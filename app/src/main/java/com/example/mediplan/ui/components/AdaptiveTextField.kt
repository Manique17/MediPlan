package com.example.mediplan.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Um componente OutlinedTextField adaptável que muda as suas cores
 * com base no tema atual da aplicação (claro ou escuro) e permite
 * personalização adicional da cor do texto.
 *
 * "Adaptive OutlinedTextField that changes colors based on the current theme"
 * (OutlinedTextField adaptável que muda as cores com base no tema atual)
 */
@Composable // Indica que esta é uma função que descreve uma parte da UI
fun AdaptiveOutlinedTextField(
    value: String, // O texto atual dentro do campo
    onValueChange: (String) -> Unit, // Função chamada quando o texto muda (o utilizador escreve)
    label: @Composable () -> Unit, // O texto da etiqueta que aparece acima ou dentro do campo
    modifier: Modifier = Modifier, // Modificador para estilizar ou dar layout (opcional, padrão é sem modificações)
    placeholder: @Composable (() -> Unit)? = null, // Texto de exemplo que aparece quando o campo está vazio (opcional)
    supportingText: @Composable (() -> Unit)? = null, // Texto de ajuda ou erro que aparece abaixo do campo (opcional)
    isError: Boolean = false, // Se verdadeiro, mostra o campo em estado de erro (ex: contorno vermelho)
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default, // Configurações do teclado (ex: numérico, email)
    singleLine: Boolean = false, // Se verdadeiro, o texto fica numa só linha (não quebra)
    maxLines: Int = Int.MAX_VALUE, // Número máximo de linhas que o campo pode ter
    trailingIcon: @Composable (() -> Unit)? = null, // Ícone que aparece no final do campo (opcional)
    visualTransformation: VisualTransformation = VisualTransformation.None, // Para mudar como o texto é mostrado (ex: **** para passwords)
    textColor: Color? = null // <-- Parâmetro opcional para definir uma cor específica para o texto do input
) {
    // Obtém cores do tema Material Design atual
    val primaryColor = MaterialTheme.colorScheme.primary // Cor primária do tema (ex: para foco)
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface // Cor para texto/ícones em cima de superfícies do tema
    val errorColor = Color.Red // Define a cor vermelha para estados de erro

    // Usa o componente OutlinedTextField do Material 3
    OutlinedTextField(
        value = value, // Passa o texto atual
        onValueChange = onValueChange, // Passa a função para quando o texto muda
        label = label, // Passa a etiqueta
        placeholder = placeholder, // Passa o texto de exemplo
        supportingText = supportingText, // Passa o texto de ajuda/erro
        isError = isError, // Passa o estado de erro
        keyboardOptions = keyboardOptions, // Passa as opções de teclado
        singleLine = singleLine, // Passa a configuração de linha única
        maxLines = maxLines, // Passa o número máximo de linhas
        trailingIcon = trailingIcon, // Passa o ícone final
        visualTransformation = visualTransformation, // Passa a transformação visual
        modifier = modifier, // Aplica quaisquer modificadores
        colors = OutlinedTextFieldDefaults.colors( // Personaliza as cores do campo de texto
            // Cores quando o campo está focado (o utilizador está a escrever nele)
            focusedBorderColor = primaryColor, // Cor da borda quando focado
            focusedLabelColor = primaryColor, // Cor da etiqueta quando focado
            cursorColor = primaryColor, // Cor do cursor de texto
            focusedTextColor = textColor ?: onSurfaceColor, // Cor do texto quando focado: usa 'textColor' se fornecido, senão a cor padrão 'onSurfaceColor'

            // Cores quando o campo não está focado
            unfocusedBorderColor = onSurfaceColor.copy(alpha = 0.7f), // Cor da borda quando não focado (ligeiramente transparente)
            unfocusedLabelColor = onSurfaceColor, // Cor da etiqueta quando não focado
            unfocusedTextColor = textColor ?: onSurfaceColor, // Cor do texto quando não focado: usa 'textColor' se fornecido, senão 'onSurfaceColor'

            // Cores para o estado de erro
            errorBorderColor = errorColor, // Cor da borda em erro
            errorLabelColor = errorColor, // Cor da etiqueta em erro
            errorCursorColor = errorColor, // Cor do cursor em erro
            errorSupportingTextColor = errorColor, // Cor do texto de ajuda/erro em erro
            errorTrailingIconColor = errorColor, // Cor do ícone final em erro
            errorLeadingIconColor = errorColor, // Cor do ícone inicial em erro (se houvesse)
            errorPrefixColor = errorColor, // Cor do prefixo em erro (se houvesse)
            errorSuffixColor = errorColor // Cor do sufixo em erro (se houvesse)
        )
    )
}

