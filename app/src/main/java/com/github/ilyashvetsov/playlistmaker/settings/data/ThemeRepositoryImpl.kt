package com.github.ilyashvetsov.playlistmaker.settings.data

import androidx.appcompat.app.AppCompatDelegate
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeRepository

class ThemeRepositoryImpl(
    private val themeStorage: ThemeStorage
) : ThemeRepository {
    override fun isDarkTheme(): Boolean {
        return themeStorage.isDarkTheme()
    }

    override fun setDarkTheme(darkTheme: Boolean) {
        themeStorage.setDarkTheme(darkTheme)
        applyCurrentTheme()
    }

    override fun applyCurrentTheme() {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme()) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
