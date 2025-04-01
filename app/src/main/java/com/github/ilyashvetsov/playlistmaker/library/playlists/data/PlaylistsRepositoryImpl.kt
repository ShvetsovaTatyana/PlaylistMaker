package com.github.ilyashvetsov.playlistmaker.library.playlists.data

import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.PlaylistsDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.TracksDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsRepository
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

class PlaylistsRepositoryImpl(appDatabase: AppDatabase) : PlaylistsRepository {
    private val playlistsDao: PlaylistsDao = appDatabase.getPlaylistsDao()
    private val tracksDao: TracksDao = appDatabase.getTracksDao()

    override fun addPlaylist(playlist: Playlist) {
        playlistsDao.insertPlaylist(playlistEntity = playlist.toEntity())
    }

    override fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        val mutableList = mutableListOf<Int>()
        mutableList.addAll(playlist.trackIds)
        mutableList.add(track.trackId)
        playlistsDao.updatePlaylist(
            playlistEntity = playlist.copy(trackIds = mutableList).toEntity()
        )
        tracksDao.insertTrack(trackEntity = track.toTrackEntity())
    }

    override fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        val mutableList = mutableListOf<Int>()
        mutableList.addAll(playlist.trackIds)
        mutableList.remove(track.trackId)
        playlistsDao.updatePlaylist(
            playlistEntity = playlist.copy(trackIds = mutableList).toEntity()
        )
    }

    override fun removePlaylist(playlist: Playlist) {
        playlistsDao.deletePlaylist(playlistEntity = playlist.toEntity())
    }

    override fun getPlaylists(): List<Playlist> {
        return playlistsDao.getPlaylists().map { playlistEntity -> playlistEntity.toDomain() }
    }

    override fun getTracks(playlist: Playlist): List<Track> {
        return tracksDao.getTracks(playlist.trackIds).map { trackEntity -> trackEntity.toDomain() }
    }
}
