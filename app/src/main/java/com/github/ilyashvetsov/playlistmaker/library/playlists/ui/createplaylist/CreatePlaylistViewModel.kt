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

    private var editablePlaylist: Playlist? = null
    val isEditMode: Boolean
        get() = editablePlaylist != null

    private val _screenState: MutableLiveData<CreatePlaylistScreenState> =
        MutableLiveData(CreatePlaylistScreenState.EMPTY)
    val screenState: LiveData<CreatePlaylistScreenState> = _screenState

    fun init(editablePlaylist: Playlist?) {
        editablePlaylist?.let { playlist ->
            this.editablePlaylist = playlist
            _screenState.value = CreatePlaylistScreenState(
                name = playlist.name,
                description = playlist.description,
                imagePath = playlist.imagePath
            )
        }
    }

    fun setImagePath(imagePath: String) {
        _screenState.value = _screenState.value?.copy(imagePath = imagePath)
    }

    fun onNameChanged(name: String) {
        _screenState.value = _screenState.value?.copy(name = name)
    }

    fun onDescriptionChanged(description: String) {
        _screenState.value = _screenState.value?.copy(description = description)
    }

    fun onSaveButtonClicked() = viewModelScope.launch {
        screenState.value?.let { data ->
            if (isEditMode) {
                interactor.updatePlaylist(
                    playlist = editablePlaylist!!.copy(
                        name = data.name,
                        description = data.description,
                        imagePath = data.imagePath,
                    )
                )
            } else {
                interactor.addPlaylist(
                    playlist = Playlist(
                        id = 0,
                        name = data.name,
                        description = data.description,
                        imagePath = data.imagePath,
                        trackIds = emptyList()
                    )
                )
            }
        }
    }
}
