package com.example.mediplan.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mediplan.utils.ThemePreferences

// ViewModel para gerenciar as configurações do tema da aplicação
class SettingsViewModel(private val context: Context) : ViewModel() {
    private val themePreferences = ThemePreferences(context)

    // Variável para armazenar o estado do tema (modo escuro ou claro)
    var isDarkMode by mutableStateOf(false)
        private set

    // Variável para armazenar o estado do tema (modo escuro ou claro)
    init {
        // Inicializa o tema baseado nas preferências salvas ou no tema do sistema
        isDarkMode = if (themePreferences.isThemeSet()) {
            themePreferences.isDarkMode()
        } else {
            false // Padrão para modo claro se não foi definido
        }
    }

    // Funções para alternar entre os temas
    fun toggleTheme() {
        isDarkMode = !isDarkMode
        themePreferences.setDarkMode(isDarkMode)
    }

    // Função para definir o tema explicitamente
    fun setTheme(darkMode: Boolean) {
        isDarkMode = darkMode
        themePreferences.setDarkMode(darkMode)
    }

    // Função para reiniciar o tema para o tema do sistema
    fun resetThemeToSystem() {
        themePreferences.clearThemePreferences()
        // Será necessário reiniciar o app ou recompor para aplicar o tema do sistema
    }
}