package com.github.ilyashvetsov.playlistmaker.library.favorite.ui

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

sealed class FavoriteScreenState {
    object Loading : FavoriteScreenState()

    object Empty : FavoriteScreenState()

    class Data(val trackList: List<Track>) : FavoriteScreenState()
}