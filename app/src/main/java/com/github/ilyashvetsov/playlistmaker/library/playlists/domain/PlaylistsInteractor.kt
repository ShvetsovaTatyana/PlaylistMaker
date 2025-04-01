package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractor
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

interface PlaylistsInteractor {
    fun addPlaylist(playlist: Playlist)
    fun addTrackToPlaylist(track: Track, playlist: Playlist)
    fun removeTrackFromPlaylist(track: Track, playlist: Playlist)
    fun getPlaylists(): List<Playlist>
    fun getTracks(playlist: Playlist): List<Track>
    fun getAllTime(playlist: Playlist): Int
    fun share(playlist: Playlist)
}

class PlaylistsInteractorImpl(
    private val repository: PlaylistsRepository,
    private val sharingInteractor: SharingInteractor
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

    override fun share(playlist: Playlist) {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        val trackList = getTracks(playlist).mapIndexed { index, track ->
            "${index + 1}. ${track.artistName} - ${track.trackName} (${dateFormat.format(track.trackTimeMillis)})"
        }
        val text = """
            ${playlist.name}
            ${playlist.description}
            Треков: ${playlist.trackIds.size}         
        """.trimIndent() + "\n" + trackList.joinToString("\n")
        sharingInteractor.shareText(text)
    }
}
