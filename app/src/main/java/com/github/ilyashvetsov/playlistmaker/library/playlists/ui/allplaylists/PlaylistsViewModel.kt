package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractor

class PlaylistsViewModel(
    private val interactor: PlaylistsInteractor
) : ViewModel() {
    private val _screenState: MutableLiveData<PlaylistsScreenState> = MutableLiveData(PlaylistsScreenState.Loading)
    val screenState: LiveData<PlaylistsScreenState> = _screenState

    fun updateData() {
        val playlists = interactor.getPlaylists()
        _screenState.value = if (playlists.isEmpty()) {
            PlaylistsScreenState.Empty
        } else {
            PlaylistsScreenState.Data(playlists)
        }
    }
}
