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
 * Adaptive OutlinedTextField that changes colors based on the current theme
 */
@Composable
fun AdaptiveOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color? = null // <-- Adicionado parâmetro opcional para cor do texto
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val errorColor = Color.Red

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        maxLines = maxLines,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = onSurfaceColor.copy(alpha = 0.7f),
            focusedLabelColor = primaryColor,
            unfocusedLabelColor = onSurfaceColor,
            cursorColor = primaryColor,
            unfocusedTextColor = textColor ?: onSurfaceColor, // <-- Usa cor preta se fornecida
            focusedTextColor = textColor ?: onSurfaceColor,   // <-- Usa cor preta se fornecida
            errorBorderColor = errorColor,
            errorLabelColor = errorColor,
            errorCursorColor = errorColor,
            errorSupportingTextColor = errorColor,
            errorTrailingIconColor = errorColor,
            errorLeadingIconColor = errorColor,
            errorPrefixColor = errorColor,
            errorSuffixColor = errorColor
        )
    )
}