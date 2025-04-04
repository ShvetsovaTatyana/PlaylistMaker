package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractor
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val interactor: PlaylistsInteractor,
) : ViewModel() {

    private val _playlistState: MutableLiveData<Playlist?> = MutableLiveData(null)
    val playlistState: LiveData<Playlist?> = _playlistState

    private val _allTimeState: MutableLiveData<Int?> = MutableLiveData(null)
    val allTimeState: LiveData<Int?> = _allTimeState

    private val _screenState: MutableLiveData<PlaylistScreenState> = MutableLiveData(PlaylistScreenState.Loading)
    val screenState: LiveData<PlaylistScreenState> = _screenState

    fun update(playlistId: Int) = viewModelScope.launch {
        interactor.getPlaylistById(playlistId).collect { playlist ->
            _playlistState.value = playlist
            viewModelScope.launch {
                _allTimeState.value = interactor.getAllTime(playlist) / 1000 / 60
                val trackList = interactor.getTracks(playlist).first()
                _screenState.value = if (trackList.isEmpty()) {
                    PlaylistScreenState.Empty
                } else {
                    PlaylistScreenState.Data(trackList)
                }
            }
        }
    }

    fun removeTrack(track: Track) = viewModelScope.launch {
        playlistState.value?.let { playlist ->
            interactor.removeTrackFromPlaylist(track, playlist)
        }
    }

    fun removePlaylist() = viewModelScope.launch {
        playlistState.value?.let { playlist ->
            interactor.removePlaylist(playlist)
        }
    }

    fun share() = playlistState.value?.let { playlist ->
        val trackList = if (screenState.value is PlaylistScreenState.Data) {
            (screenState.value as PlaylistScreenState.Data).trackList
        } else {
            emptyList()
        }
        interactor.share(playlist, trackList)
    }
}
