package com.example.mediplan.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mediplan.utils.ThemePreferences

class SettingsViewModel(private val context: Context) : ViewModel() {
    private val themePreferences = ThemePreferences(context)
    
    var isDarkMode by mutableStateOf(false)
        private set
    
    init {
        // Inicializa o tema baseado nas preferências salvas ou no tema do sistema
        isDarkMode = if (themePreferences.isThemeSet()) {
            themePreferences.isDarkMode()
        } else {
            false // Padrão para modo claro se não foi definido
        }
    }
    
    fun toggleTheme() {
        isDarkMode = !isDarkMode
        themePreferences.setDarkMode(isDarkMode)
    }
    
    fun setTheme(darkMode: Boolean) {
        isDarkMode = darkMode
        themePreferences.setDarkMode(darkMode)
    }
    
    fun resetThemeToSystem() {
        themePreferences.clearThemePreferences()
        // Será necessário reiniciar o app ou recompor para aplicar o tema do sistema
    }
}