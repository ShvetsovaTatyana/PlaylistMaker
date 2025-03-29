package com.github.ilyashvetsov.playlistmaker

import android.app.Application
import com.github.ilyashvetsov.playlistmaker.library.root.di.libraryModule
import com.github.ilyashvetsov.playlistmaker.player.di.playerModule
import com.github.ilyashvetsov.playlistmaker.search.di.searchModule
import com.github.ilyashvetsov.playlistmaker.settings.di.settingsModule
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractor
import com.github.ilyashvetsov.playlistmaker.sharing.di.sharingModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                libraryModule,
                playerModule,
                searchModule,
                settingsModule,
                sharingModule
            )
        }
        val themeInteractor: ThemeInteractor = get()
        themeInteractor.applyCurrentTheme()
    }
}
