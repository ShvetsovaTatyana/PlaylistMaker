package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

interface PlaylistsInteractor {
    fun addPlaylist(playlist: Playlist)
    fun addTrackToPlaylist(track: Track, playlist: Playlist)
    fun removeTrackFromPlaylist(track: Track, playlist: Playlist)
    fun getPlaylists(): List<Playlist>
    fun getTracks(playlist: Playlist): List<Track>
    fun getAllTime(playlist: Playlist): Int
}

class PlaylistsInteractorImpl(
    private val repository: PlaylistsRepository
) : PlaylistsInteractor {
    override fun addPlaylist(playlist: Playlist) {
        repository.addPlaylist(playlist)
    }

    override fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        repository.addTrackToPlaylist(track, playlist)
    }

    override fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        repository.removeTrackFromPlaylist(track, playlist)
    }

    override fun getPlaylists(): List<Playlist> {
        return repository.getPlaylists()
    }

    override fun getTracks(playlist: Playlist): List<Track> {
        return repository.getTracks(playlist)
    }

    override fun getAllTime(playlist: Playlist): Int {
        return getTracks(playlist).sumOf { track -> track.trackTimeMillis }
    }
}
