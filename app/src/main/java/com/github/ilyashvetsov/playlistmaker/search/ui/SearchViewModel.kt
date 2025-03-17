package com.github.ilyashvetsov.playlistmaker.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchHistoryInteractor
import com.github.ilyashvetsov.playlistmaker.search.domain.SearchTracksUseCase
import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchTracksUseCase: SearchTracksUseCase,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<SearchScreenState> = MutableLiveData(SearchScreenState.Init)
    val screenState: LiveData<SearchScreenState> = _screenState

    fun searchTracks(expression: String) {
        if (expression.isEmpty()) {
            handleEmptySearchText()
        } else {
            _screenState.value = SearchScreenState.Loading
            viewModelScope.launch {
                searchTracksUseCase
                    .execute(expression)
                    .collect { pair ->
                        pair.first?.let { trackList ->
                            _screenState.postValue(
                                if (trackList.isEmpty()) {
                                    SearchScreenState.Empty
                                } else {
                                    SearchScreenState.SearchData(trackList)
                                }
                            )
                        }
                        pair.second?.let { error ->
                            _screenState.postValue(
                                SearchScreenState.Error(error)
                            )
                        }
                    }
            }
        }
    }

    fun saveTrack(track: Track) {
        searchHistoryInteractor.saveTrack(track)
    }

    fun clearHistory() {
        _screenState.value = SearchScreenState.Init
        searchHistoryInteractor.clearTrackList()
    }

    fun clearButtonClick() {
        handleEmptySearchText()
    }

    fun changeFocus(textSearch: String, hasFocus: Boolean) {
        val historyTrackList = searchHistoryInteractor.getTrackList()
        if (hasFocus && textSearch.isEmpty() && historyTrackList.isNotEmpty()) {
            _screenState.value = SearchScreenState.HistoryData(historyTrackList)
        }
    }

    private fun handleEmptySearchText() {
        val historyTrackList = searchHistoryInteractor.getTrackList()
        _screenState.value = if (historyTrackList.isEmpty()) {
            SearchScreenState.Init
        } else {
            SearchScreenState.HistoryData(historyTrackList)
        }
    }
}
