package com.github.ilyashvetsov.playlistmaker.library.root.di

import androidx.room.Room
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.FavoriteRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteInteractor
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteInteractorImpl
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteRepository
import com.github.ilyashvetsov.playlistmaker.library.favorite.ui.FavoriteTracksViewModel
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.PlaylistsRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractor
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractorImpl
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsRepository
import com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists.PlaylistsViewModel
import com.github.ilyashvetsov.playlistmaker.library.playlists.ui.createplaylist.CreatePlaylistViewModel
import com.github.ilyashvetsov.playlistmaker.library.playlists.ui.playlist.PlaylistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val libraryModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }

    singleOf(::FavoriteRepositoryImpl) { bind<FavoriteRepository>() }
    factoryOf(::FavoriteInteractorImpl) { bind<FavoriteInteractor>() }

    singleOf(::PlaylistsRepositoryImpl) { bind<PlaylistsRepository>() }
    factoryOf(::PlaylistsInteractorImpl) { bind<PlaylistsInteractor>() }

    viewModelOf(::FavoriteTracksViewModel)
    viewModelOf(::PlaylistsViewModel)
    viewModelOf(::CreatePlaylistViewModel)
    viewModelOf(::PlaylistViewModel)
}
