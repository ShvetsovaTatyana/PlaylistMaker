package com.github.ilyashvetsov.playlistmaker.settings.domain

interface ThemeInteractor {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(darkTheme: Boolean)
    fun applyCurrentTheme()
}

class ThemeInteractorImpl(
    private val repository: ThemeRepository
) : ThemeInteractor {
    override fun isDarkTheme(): Boolean {
        return repository.isDarkTheme()
    }

    override fun setDarkTheme(darkTheme: Boolean) {
        repository.setDarkTheme(darkTheme)
    }

    override fun applyCurrentTheme() {
        repository.applyCurrentTheme()
    }
}
