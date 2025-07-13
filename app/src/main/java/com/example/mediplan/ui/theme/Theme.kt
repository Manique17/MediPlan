package com.example.mediplan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//define as cores do tema
private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    secondary = DarkBlue,
    tertiary = LightGreenVariant,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White
)

// Define as cores do tema claro
private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    secondary = LightBlue,
    tertiary = LightGreenVariant,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

// Define o tema MediPlan
@Composable
fun MediPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    // Define o tema Material com as cores e tipografia
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}