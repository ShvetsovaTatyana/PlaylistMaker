package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.createplaylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsInteractor
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val interactor: PlaylistsInteractor,
) : ViewModel() {
    private val _screenState: MutableLiveData<CreatePlaylistScreenState> =
        MutableLiveData(CreatePlaylistScreenState.EMPTY)
    val screenState: LiveData<CreatePlaylistScreenState> = _screenState

    fun setImagePath(imagePath: String) {
        _screenState.value = _screenState.value?.copy(imagePath = imagePath)
    }

    fun onNameChanged(name: String) {
        _screenState.value = _screenState.value?.copy(name = name)
    }

    fun onDescriptionChanged(description: String) {
        _screenState.value = _screenState.value?.copy(description = description)
    }

    fun onCreateButtonClicked() = viewModelScope.launch {
        screenState.value?.let {
            interactor.addPlaylist(
                playlist = Playlist(
                    id = 0,
                    name = it.name,
                    description = it.description,
                    imagePath = it.imagePath,
                    trackIds = emptyList()
                )
            )
        }
    }
}
