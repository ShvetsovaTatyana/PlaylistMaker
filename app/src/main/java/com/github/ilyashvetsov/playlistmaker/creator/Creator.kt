package com.github.ilyashvetsov.playlistmaker.creator

import android.content.Context
import android.content.SharedPreferences
import com.github.ilyashvetsov.playlistmaker.search.data.network.RetrofitNetworkClient
import com.github.ilyashvetsov.playlistmaker.player.data.AudioPlayerRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.search.data.repository.SearchHistoryRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractor
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractorImpl
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchHistoryInteractor
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchHistoryInteractorImpl
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerRepository
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.SearchHistoryRepository
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.TrackRepository
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchTracksUseCase
import com.github.ilyashvetsov.playlistmaker.settings.data.ThemeRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.settings.data.ThemeStorage
import com.github.ilyashvetsov.playlistmaker.settings.data.ThemeStorageImpl
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractor
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractorImpl
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeRepository
import com.github.ilyashvetsov.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.github.ilyashvetsov.playlistmaker.sharing.domain.ExternalNavigator
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractor
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractorImpl

object Creator {
    private const val HISTORY_SHARED_PREFERENCES = "history_shared_preferences"
    private const val THEME_SHARED_PREFERENCES = "theme_shared_preferences"

    // Data

    private fun getSearchHistorySharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(HISTORY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun getThemeSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(THEME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun getThemeStorage(context: Context): ThemeStorage {
       return ThemeStorageImpl(
           getThemeSharedPreferences(context)
       )
    }

    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(
            RetrofitNetworkClient()
        )
    }

    private fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(
            getSearchHistorySharedPreferences(context)
        )
    }

    private fun getAudioPlayerRepository(): AudioPlayerRepository {
        return AudioPlayerRepositoryImpl()
    }

    private fun getExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }

    private fun getThemeRepository(context: Context): ThemeRepository {
        return ThemeRepositoryImpl(
            getThemeStorage(context)
        )
    }

    // Domain

    fun getSearchTracksUseCase(): SearchTracksUseCase {
        return SearchTracksUseCase(
            getTrackRepository()
        )
    }

    fun getSearchHistoryInteractor(context: Context): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(
            getSearchHistoryRepository(context)
        )
    }

    fun getAudioPlayerInteractor(): AudioPlayerInteractor {
        return AudioPlayerInteractorImpl(
            getAudioPlayerRepository()
        )
    }

    fun getSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImpl(
            getExternalNavigator(context)
        )
    }

    fun getThemeInteractor(context: Context): ThemeInteractor {
        return ThemeInteractorImpl(
            getThemeRepository(context)
        )
    }
}
