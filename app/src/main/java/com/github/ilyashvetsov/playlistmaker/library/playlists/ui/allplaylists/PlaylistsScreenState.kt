package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist

sealed class PlaylistsScreenState {
    object Loading : PlaylistsScreenState()

    object Empty : PlaylistsScreenState()

    class Data(val playlists: List<Playlist>) : PlaylistsScreenState()
}
