package com.github.ilyashvetsov.playlistmaker.player.domain

interface AudioPlayerInteractor {
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

class AudioPlayerInteractorImpl(
    private val repository: AudioPlayerRepository
): AudioPlayerInteractor {

    override fun preparePlayer(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit,
        onUpdateUI: (Int) -> Unit
    ) {
        repository.preparePlayer(url, onPrepared, onCompletion, onUpdateUI)
    }

    override fun startPlayer() {
        repository.startPlayer()
    }

    override fun pausePlayer() {
        repository.pausePlayer()
    }

    override fun releasePlayer() {
        repository.releasePlayer()
    }
}
