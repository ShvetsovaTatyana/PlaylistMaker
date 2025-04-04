package com.github.ilyashvetsov.playlistmaker.player.data

import android.media.MediaPlayer
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioPlayerRepositoryImpl(
    private var mediaPlayer: MediaPlayer
): AudioPlayerRepository {
    private var updateUI: ((Int) -> Unit)? = null
    private var job: Job? = null

    override fun preparePlayer(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit,
        onUpdateUI: (Int) -> Unit
    ) {
        mediaPlayer.apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { onPrepared.invoke() }
            setOnCompletionListener {
                onCompletion()
                job?.cancel()
            }
        }
        updateUI = onUpdateUI
    }

    override fun startPlayer() {
        mediaPlayer.start()
        job = CoroutineScope(Dispatchers.Default).launch {
            while (mediaPlayer.isPlaying) {
                updateUI?.invoke(mediaPlayer.currentPosition)
                delay(JOB_DELAY)
            }
        }
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        job?.cancel()
    }

    override fun resetPlayer() {
        mediaPlayer.reset()
    }

    override fun releasePlayer() {
        mediaPlayer.release()
        job?.cancel()
    }

    companion object {
        private const val JOB_DELAY = 300L
    }
}
