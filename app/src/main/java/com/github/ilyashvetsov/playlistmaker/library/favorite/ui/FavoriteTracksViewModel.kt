package com.github.ilyashvetsov.playlistmaker.library.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteInteractor
import kotlinx.coroutines.launch

class FavoriteTracksViewModel(
    private val interactor: FavoriteInteractor,
) : ViewModel() {
    private val _screenState: MutableLiveData<FavoriteScreenState> = MutableLiveData(FavoriteScreenState.Loading)
    val screenState: LiveData<FavoriteScreenState> = _screenState

    fun init() = viewModelScope.launch {
        interactor.getTracks().collect { trackList ->
            _screenState.value = if (trackList.isEmpty()) {
                FavoriteScreenState.Empty
            } else {
                FavoriteScreenState.Data(trackList)
            }
        }
    }
}
