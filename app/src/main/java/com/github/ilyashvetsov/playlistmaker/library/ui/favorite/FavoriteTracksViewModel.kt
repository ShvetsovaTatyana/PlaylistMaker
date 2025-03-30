package com.github.ilyashvetsov.playlistmaker.library.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ilyashvetsov.playlistmaker.library.domain.LibraryInteractor

class FavoriteTracksViewModel(
    private val libraryInteractor: LibraryInteractor,
) : ViewModel() {
    private val _screenState: MutableLiveData<FavoriteScreenState> = MutableLiveData(FavoriteScreenState.Loading)
    val screenState: LiveData<FavoriteScreenState> = _screenState

    fun updateData() {
        val trackList = libraryInteractor.getTracks()
        _screenState.value = if (trackList.isEmpty()) {
            FavoriteScreenState.Empty
        } else {
            FavoriteScreenState.Data(trackList)
        }
    }
}
