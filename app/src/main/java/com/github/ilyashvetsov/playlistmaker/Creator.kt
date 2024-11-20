package com.github.ilyashvetsov.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import com.github.ilyashvetsov.playlistmaker.data.network.RetrofitNetworkClient
import com.github.ilyashvetsov.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.data.repository.TrackRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.domain.interactors.SearchHistoryInteractor
import com.github.ilyashvetsov.playlistmaker.domain.interactors.SearchHistoryInteractorImpl
import com.github.ilyashvetsov.playlistmaker.domain.repository.SearchHistoryRepository
import com.github.ilyashvetsov.playlistmaker.domain.repository.TrackRepository
import com.github.ilyashvetsov.playlistmaker.domain.usecases.SearchTracksUseCase

object Creator {
    private const val HISTORY_SHARED_PREFERENCES = "history_shared_preferences"

    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(
            RetrofitNetworkClient()
        )
    }

    fun getSearchTracksUseCase(): SearchTracksUseCase {
        return SearchTracksUseCase(
            getTrackRepository()
        )
    }

    private fun getSearchHistorySharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(HISTORY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(
            getSearchHistorySharedPreferences(context)
        )
    }

    fun getSearchHistoryInteractor(context: Context): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(
            getSearchHistoryRepository(context)
        )
    }

}
