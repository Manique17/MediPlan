package com.example.mediplan.utils

import android.content.Context
import android.content.SharedPreferences

class ThemePreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_THEME_SET = "theme_set"
    }
    
    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }
    
    fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_DARK_MODE, isDarkMode)
            .putBoolean(KEY_THEME_SET, true)
            .apply()
    }
    
    fun isThemeSet(): Boolean {
        return sharedPreferences.getBoolean(KEY_THEME_SET, false)
    }
    
    fun clearThemePreferences() {
        sharedPreferences.edit()
            .remove(KEY_DARK_MODE)
            .remove(KEY_THEME_SET)
            .apply()
    }
}