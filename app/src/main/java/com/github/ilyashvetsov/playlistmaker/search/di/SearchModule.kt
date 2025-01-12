package com.github.ilyashvetsov.playlistmaker.search.di

import android.content.Context
import com.github.ilyashvetsov.playlistmaker.search.data.network.ItunesApiService
import com.github.ilyashvetsov.playlistmaker.search.data.network.NetworkClient
import com.github.ilyashvetsov.playlistmaker.search.data.network.RetrofitNetworkClient
import com.github.ilyashvetsov.playlistmaker.search.data.repository.SearchHistoryRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchHistoryInteractor
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchHistoryInteractorImpl
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchTracksUseCase
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.SearchHistoryRepository
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.TrackRepository
import com.github.ilyashvetsov.playlistmaker.search.ui.SearchViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val HISTORY_SHARED_PREFERENCES = "history_shared_preferences"

val searchModule = module {
    single<ItunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<ItunesApiService>()
    }

    singleOf(::RetrofitNetworkClient) { bind<NetworkClient>() }

    single { androidContext().getSharedPreferences(HISTORY_SHARED_PREFERENCES, Context.MODE_PRIVATE) }
    factory { Gson() }

    singleOf(::SearchHistoryRepositoryImpl) { bind<SearchHistoryRepository>() }
    singleOf(::TrackRepositoryImpl) { bind<TrackRepository>() }

    factoryOf(::SearchHistoryInteractorImpl) { bind<SearchHistoryInteractor>() }
    factoryOf(::SearchTracksUseCase)

    viewModelOf(::SearchViewModel)
}
