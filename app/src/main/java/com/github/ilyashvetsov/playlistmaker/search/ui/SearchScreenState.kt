package com.github.ilyashvetsov.playlistmaker.search.ui

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

sealed class SearchScreenState {
    object Init : SearchScreenState()

    object Loading : SearchScreenState()

    class SearchData(val trackList: List<Track>) : SearchScreenState()

    class HistoryData(val trackList: List<Track>) : SearchScreenState()

    object Empty : SearchScreenState()

    class Error(val message: String) : SearchScreenState()
}
