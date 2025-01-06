package com.github.ilyashvetsov.playlistmaker.settings.domain

interface ThemeRepository {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(darkTheme: Boolean)
    fun applyCurrentTheme()
}
