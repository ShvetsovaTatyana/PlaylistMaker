package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.playlist

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

sealed class PlaylistScreenState {
    object Loading : PlaylistScreenState()

    object Empty : PlaylistScreenState()

    class Data(val trackList: List<Track>) : PlaylistScreenState()
}
