package com.github.ilyashvetsov.playlistmaker.settings.di

import android.content.Context
import com.github.ilyashvetsov.playlistmaker.settings.data.ThemeRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.settings.data.ThemeStorage
import com.github.ilyashvetsov.playlistmaker.settings.data.ThemeStorageImpl
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractor
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractorImpl
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeRepository
import com.github.ilyashvetsov.playlistmaker.settings.ui.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val THEME_SHARED_PREFERENCES = "theme_shared_preferences"

val settingsModule = module {
    single { androidContext().getSharedPreferences(THEME_SHARED_PREFERENCES, Context.MODE_PRIVATE) }

    singleOf(::ThemeStorageImpl) { bind<ThemeStorage>() }

    singleOf(::ThemeRepositoryImpl) { bind<ThemeRepository>() }

    factoryOf(::ThemeInteractorImpl) { bind<ThemeInteractor>() }

    viewModelOf(::SettingsViewModel)
}
