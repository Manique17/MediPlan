package com.example.mediplan.utils

import android.content.Context
import android.content.SharedPreferences

// tema de preferências do usuário
class ThemePreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_THEME_SET = "theme_set"
    }

    // Verifica se o modo escuro está ativado
    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    // Define o modo escuro
    fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_DARK_MODE, isDarkMode)
            .putBoolean(KEY_THEME_SET, true)
            .apply()
    }

    // Verifica se o tema foi definido
    fun isThemeSet(): Boolean {
        return sharedPreferences.getBoolean(KEY_THEME_SET, false)
    }

    // Limpa as preferências de tema
    fun clearThemePreferences() {
        sharedPreferences.edit()
            .remove(KEY_DARK_MODE)
            .remove(KEY_THEME_SET)
            .apply()
    }
}