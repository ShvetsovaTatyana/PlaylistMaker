package com.github.ilyashvetsov.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteInteractor
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractor
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.player.domain.AudioPlayerInteractor
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractor: AudioPlayerInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val playlistsInteractor: PlaylistsInteractor,
) : ViewModel() {
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    private lateinit var track: Track

    private val _playerState = MutableLiveData(STATE_DEFAULT)
    val playerState: LiveData<Int> = _playerState

    private val _isFavoriteState: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isFavoriteState: LiveData<Boolean?> = _isFavoriteState

    private val _timeSing = MutableLiveData("00:00")
    val timeSing: LiveData<String> = _timeSing

    private val _playlistsState: MutableLiveData<List<Playlist>> = MutableLiveData(emptyList())
    val playlistsState: LiveData<List<Playlist>> = _playlistsState

    fun init(track: Track) {
        this.track = track
        viewModelScope.launch(Dispatchers.IO) {
            _isFavoriteState.postValue(favoriteInteractor.isFavorite(track))
            playlistsInteractor.getPlaylists().collect { playlists ->
                _playlistsState.postValue(playlists)
            }
        }
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

    fun addTrackToFavorite() = viewModelScope.launch(Dispatchers.IO) {
        if (favoriteInteractor.isFavorite(track)) {
            favoriteInteractor.removeTrack(track)
        } else {
            favoriteInteractor.addTrack(track)
        }
        _isFavoriteState.postValue(favoriteInteractor.isFavorite(track))
    }

    fun onPlaylistClicked(playlist: Playlist): Boolean {
        return if (track.trackId in playlist.trackIds) {
            false
        } else {
            viewModelScope.launch {
                playlistsInteractor.addTrackToPlaylist(track, playlist)
            }
            true
        }
    }

    companion object {
        const val STATE_DEFAULT = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
    }
}
