package com.github.ilyashvetsov.playlistmaker.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractor
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val themeInteractor: ThemeInteractor
) : ViewModel() {

    fun isDarkTheme(): Boolean {
        return themeInteractor.isDarkTheme()
    }

    fun setDarkTheme(darkTheme: Boolean) {
        themeInteractor.setDarkTheme(darkTheme)
    }

    fun shareApp() {
        sharingInteractor.shareApp()
    }

    fun openTerms() {
        sharingInteractor.openTerms()
    }

    fun openSupport() {
        sharingInteractor.openSupport()
    }

    companion object {
        fun getViewModelFactory(
            sharingInteractor: SharingInteractor,
            themeInteractor: ThemeInteractor
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(sharingInteractor, themeInteractor)
            }
        }
    }
}
