package com.github.ilyashvetsov.playlistmaker.domain.repository

interface AudioPlayerRepository {
    fun preparePlayer(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit,
        onUpdateUI: (Int) -> Unit
    )
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
}