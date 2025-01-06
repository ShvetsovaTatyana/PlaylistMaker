package com.github.ilyashvetsov.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractor
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractor: AudioPlayerInteractor
) : ViewModel() {

    private val _playerState = MutableLiveData(STATE_DEFAULT)
    val playerState: LiveData<Int> = _playerState

    private val _timeSing = MutableLiveData("00:00")
    val timeSing: LiveData<String> = _timeSing

    fun preparePlayer(url: String) {
        audioPlayerInteractor.preparePlayer(
            url = url,
            onPrepared = {
                _playerState.value = STATE_PREPARED
            },
            onCompletion = {
                _playerState.value = STATE_PREPARED
                _timeSing.value = "00:00"
            },
            onUpdateUI = { currentPosition ->
                _timeSing.value = SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentPosition)
            }
        )
    }

    fun startPlayer() {
        _playerState.value = STATE_PLAYING
        audioPlayerInteractor.startPlayer()
    }

    fun pausePlayer() {
        _playerState.value = STATE_PAUSED
        audioPlayerInteractor.pausePlayer()
    }

    fun playbackControl() {
        when (playerState.value) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    fun releasePlayer() {
        audioPlayerInteractor.releasePlayer()
    }

    companion object {
        const val STATE_DEFAULT = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3

        fun getViewModelFactory(
            audioPlayerInteractor: AudioPlayerInteractor
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AudioPlayerViewModel(audioPlayerInteractor)
            }
        }
    }
}
