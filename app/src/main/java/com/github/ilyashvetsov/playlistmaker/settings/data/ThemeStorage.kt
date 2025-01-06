package com.github.ilyashvetsov.playlistmaker.settings.data

import android.content.SharedPreferences
import androidx.core.content.edit

interface ThemeStorage {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(darkTheme: Boolean)
}

class ThemeStorageImpl(
    private val sharedPreferences: SharedPreferences
) : ThemeStorage {
    override fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(DARK_THEME_KEY, false)
    }

    override fun setDarkTheme(darkTheme: Boolean) {
        sharedPreferences.edit { putBoolean(DARK_THEME_KEY, darkTheme) }
    }

    companion object {
        private const val DARK_THEME_KEY = "DARK_THEME_KEY"
    }
}
