package com.github.ilyashvetsov.playlistmaker.library.playlists.domain

import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractor
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Locale

interface PlaylistsInteractor {
    suspend fun addPlaylist(playlist: Playlist)
    suspend fun updatePlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(track: Track, playlist: Playlist)
    suspend fun removeTrackFromPlaylist(track: Track, playlist: Playlist)
    suspend fun removePlaylist(playlist: Playlist)
    fun getPlaylistById(playlistId: Int): Flow<Playlist>
    fun getPlaylists(): Flow<List<Playlist>>
    fun getTracks(playlist: Playlist): Flow<List<Track>>
    suspend fun getAllTime(playlist: Playlist): Int
    fun share(playlist: Playlist, trackList: List<Track>)
}

class PlaylistsInteractorImpl(
    private val repository: PlaylistsRepository,
    private val sharingInteractor: SharingInteractor
) : PlaylistsInteractor {

    override suspend fun addPlaylist(playlist: Playlist) {
        repository.addPlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        repository.updatePlaylist(playlist)
    }

    override suspend fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        repository.addTrackToPlaylist(track, playlist)
    }

    override suspend fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        repository.removeTrackFromPlaylist(track, playlist)
    }

    override suspend fun removePlaylist(playlist: Playlist) {
        repository.removePlaylist(playlist)
    }

    override fun getPlaylistById(playlistId: Int): Flow<Playlist> {
        return repository.getPlaylistById(playlistId)
    }

    override fun getPlaylists(): Flow<List<Playlist>> {
        return repository.getPlaylists()
    }

    override fun getTracks(playlist: Playlist): Flow<List<Track>> {
        return repository.getTracks(playlist)
    }

    override suspend fun getAllTime(playlist: Playlist): Int {
        return getTracks(playlist).first().sumOf { track -> track.trackTimeMillis }
    }

    override fun share(playlist: Playlist, trackList: List<Track>) {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        val trackListFormatted = trackList.mapIndexed { index, track ->
            "${index + 1}. ${track.artistName} - ${track.trackName} (${dateFormat.format(track.trackTimeMillis)})"
        }
        val text = """
            ${playlist.name}
            ${playlist.description}
            Треков: ${playlist.trackIds.size}         
        """.trimIndent() + "\n" + trackListFormatted.joinToString("\n")
        sharingInteractor.shareText(text)
    }
}
