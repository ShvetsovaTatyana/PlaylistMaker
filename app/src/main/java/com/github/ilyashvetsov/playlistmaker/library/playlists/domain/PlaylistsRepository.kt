package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

interface PlaylistsRepository {
    fun addPlaylist(playlist: Playlist)
    fun addTrackToPlaylist(track: Track, playlist: Playlist)
    fun removeTrackFromPlaylist(track: Track, playlist: Playlist)
    fun getPlaylists(): List<Playlist>
    fun getTracks(playlist: Playlist): List<Track>
}
