package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

interface PlaylistsInteractor {
    fun addPlaylist(playlist: Playlist)
    fun addTrackToPlaylist(track: Track, playlist: Playlist)
    fun getPlaylists(): List<Playlist>
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

    override fun getPlaylists(): List<Playlist> {
        return repository.getPlaylists()
    }
}
