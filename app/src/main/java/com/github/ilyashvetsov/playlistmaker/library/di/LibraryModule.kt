package com.github.ilyashvetsov.playlistmaker.library.di

import com.github.ilyashvetsov.playlistmaker.library.ui.FavoriteTracksViewModel
import com.github.ilyashvetsov.playlistmaker.library.ui.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val libraryModule = module {
    viewModelOf(::FavoriteTracksViewModel)
    viewModelOf(::PlaylistsViewModel)
}
