package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractor
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val interactor: PlaylistsInteractor
) : ViewModel() {
    private val _screenState: MutableLiveData<PlaylistsScreenState> = MutableLiveData(PlaylistsScreenState.Loading)
    val screenState: LiveData<PlaylistsScreenState> = _screenState

    fun init() = viewModelScope.launch {
        interactor.getPlaylists().collect { playlists ->
            _screenState.value = if (playlists.isEmpty()) {
                PlaylistsScreenState.Empty
            } else {
                PlaylistsScreenState.Data(playlists)
            }
        }
    }
}
