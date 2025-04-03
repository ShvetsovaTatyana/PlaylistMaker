package com.github.ilyashvetsov.playlistmaker.library.playlists.data

import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.PlaylistsDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.TracksDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsRepository
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(appDatabase: AppDatabase) : PlaylistsRepository {
    private val playlistsDao: PlaylistsDao = appDatabase.getPlaylistsDao()
    private val tracksDao: TracksDao = appDatabase.getTracksDao()

    override suspend fun addPlaylist(playlist: Playlist) {
        playlistsDao.insertPlaylist(playlistEntity = playlist.toEntity())
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistsDao.updatePlaylist(playlistEntity = playlist.toEntity())
    }

    override suspend fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        val mutableList = mutableListOf<Int>()
        mutableList.addAll(playlist.trackIds)
        mutableList.add(track.trackId)
        playlistsDao.updatePlaylist(
            playlistEntity = playlist.copy(trackIds = mutableList).toEntity()
        )
        tracksDao.insertTrack(trackEntity = track.toTrackEntity())
    }

    override suspend fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        val mutableList = mutableListOf<Int>()
        mutableList.addAll(playlist.trackIds)
        mutableList.remove(track.trackId)
        playlistsDao.updatePlaylist(
            playlistEntity = playlist.copy(trackIds = mutableList).toEntity()
        )
        removeTrackIfNeeded(track)
    }

    override suspend fun removePlaylist(playlist: Playlist) {
        playlistsDao.deletePlaylist(playlistEntity = playlist.toEntity())
        getTracks(playlist).first().forEach { track ->
            removeTrackIfNeeded(track)
        }
    }

    override fun getPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getPlaylists().map { list ->
            list.map { playlistEntity -> playlistEntity.toDomain() }
        }
    }

    override fun getTracks(playlist: Playlist): Flow<List<Track>> {
        return tracksDao.getTracks(playlist.trackIds).map { trackList ->
            trackList.map { trackEntity -> trackEntity.toDomain() }
        }
    }

    /**
     * Удаляет [track], если его нет ни в одном плейлисте
     */
    private suspend fun removeTrackIfNeeded(track: Track) {
        getPlaylists().first().forEach { playlist ->
            if (track.trackId in playlist.trackIds) {
                return
            }
        }
        tracksDao.deleteTrack(trackEntity = track.toTrackEntity())
    }
}
