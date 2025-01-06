package com.github.ilyashvetsov.playlistmaker

import android.app.Application
import com.github.ilyashvetsov.playlistmaker.creator.Creator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val themeInteractor = Creator.getThemeInteractor(this)
        themeInteractor.applyCurrentTheme()
    }
}
