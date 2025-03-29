package com.github.ilyashvetsov.playlistmaker.library.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteInteractor

class FavoriteTracksViewModel(
    private val interactor: FavoriteInteractor,
) : ViewModel() {
    private val _screenState: MutableLiveData<FavoriteScreenState> = MutableLiveData(FavoriteScreenState.Loading)
    val screenState: LiveData<FavoriteScreenState> = _screenState

    fun updateData() {
        val trackList = interactor.getTracks()
        _screenState.value = if (trackList.isEmpty()) {
            FavoriteScreenState.Empty
        } else {
            FavoriteScreenState.Data(trackList)
        }
    }
}
