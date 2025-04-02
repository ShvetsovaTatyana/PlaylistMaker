package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    suspend fun addPlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(track: Track, playlist: Playlist)
    suspend fun removeTrackFromPlaylist(track: Track, playlist: Playlist)
    suspend fun removePlaylist(playlist: Playlist)
    fun getPlaylists(): Flow<List<Playlist>>
    fun getTracks(playlist: Playlist): Flow<List<Track>>
}
