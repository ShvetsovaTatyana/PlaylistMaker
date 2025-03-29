package com.github.ilyashvetsov.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ilyashvetsov.playlistmaker.library.domain.LibraryInteractor
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractor
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractor: AudioPlayerInteractor,
    private val libraryInteractor: LibraryInteractor,
) : ViewModel() {
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    private val _playerState = MutableLiveData(STATE_DEFAULT)
    val playerState: LiveData<Int> = _playerState

    private val _isFavoriteState: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isFavoriteState: LiveData<Boolean?> = _isFavoriteState

    private val _timeSing = MutableLiveData("00:00")
    val timeSing: LiveData<String> = _timeSing

    fun init(track: Track) {
        _isFavoriteState.value = libraryInteractor.isFavorite(track)
    }

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
                _timeSing.postValue(dateFormat.format(currentPosition))
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

    fun addTrackToFavorite(track: Track) {
        if (libraryInteractor.isFavorite(track)) {
            libraryInteractor.removeTrack(track)
        } else {
            libraryInteractor.addTrack(track)
        }
        _isFavoriteState.value = libraryInteractor.isFavorite(track)
    }

    companion object {
        const val STATE_DEFAULT = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
    }
}
