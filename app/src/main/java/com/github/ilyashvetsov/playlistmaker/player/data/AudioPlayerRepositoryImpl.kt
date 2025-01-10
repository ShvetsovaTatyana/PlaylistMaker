package com.github.ilyashvetsov.playlistmaker.player.data

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerRepository

class AudioPlayerRepositoryImpl: AudioPlayerRepository {
    private val mediaPlayer = MediaPlayer()

    private var updateUI: ((Int) -> Unit)? = null
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            updateUI?.invoke(mediaPlayer.currentPosition)
            handler.postDelayed(this, RUNNABLE_DELAY)
        }
    }

    override fun preparePlayer(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit,
        onUpdateUI: (Int) -> Unit
    ) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onPrepared.invoke()
        }
        mediaPlayer.setOnCompletionListener {
            onCompletion()
            handler.removeCallbacks(runnable)
        }
        updateUI = onUpdateUI
    }

    override fun startPlayer() {
        mediaPlayer.start()
        handler.postDelayed(runnable, RUNNABLE_DELAY)
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        handler.removeCallbacks(runnable)
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }

    companion object {
        private const val RUNNABLE_DELAY = 300L
    }
}
