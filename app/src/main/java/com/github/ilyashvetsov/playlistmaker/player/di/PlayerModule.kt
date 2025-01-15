package com.github.ilyashvetsov.playlistmaker.player.di

import android.media.MediaPlayer
import com.github.ilyashvetsov.playlistmaker.player.data.AudioPlayerRepositoryImpl
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractor
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractorImpl
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerRepository
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val playerModule = module {
    factory { MediaPlayer() }

    factoryOf(::AudioPlayerRepositoryImpl) { bind<AudioPlayerRepository>() }

    factoryOf(::AudioPlayerInteractorImpl) { bind<AudioPlayerInteractor>() }

    viewModelOf(::AudioPlayerViewModel)
}
