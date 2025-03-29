package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist

interface PlaylistsRepository {
    fun addPlaylist(playlist: Playlist)
    fun getPlaylists(): List<Playlist>
}
