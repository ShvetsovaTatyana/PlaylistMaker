package com.github.ilyashvetsov.playlistmaker.library.di

import androidx.room.Room
import com.github.ilyashvetsov.playlistmaker.library.data.LibraryRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.library.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.library.domain.LibraryInteractor
import com.github.ilyashvetsov.playlistmaker.library.domain.LibraryInteractorImpl
import com.github.ilyashvetsov.playlistmaker.library.domain.LibraryRepository
import com.github.ilyashvetsov.playlistmaker.library.ui.favorite.FavoriteTracksViewModel
import com.github.ilyashvetsov.playlistmaker.library.ui.PlaylistsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val libraryModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .allowMainThreadQueries() // TODO удалить
            .build()
    }

    singleOf(::LibraryRepositoryImpl) { bind<LibraryRepository>() }
    factoryOf(::LibraryInteractorImpl) { bind<LibraryInteractor>() }

    viewModelOf(::FavoriteTracksViewModel)
    viewModelOf(::PlaylistsViewModel)
}
