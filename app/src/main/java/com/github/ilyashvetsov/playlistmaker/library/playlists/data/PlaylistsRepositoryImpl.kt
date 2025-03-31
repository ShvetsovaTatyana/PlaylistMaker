package com.github.ilyashvetsov.playlistmaker.library.playlists.data

import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.PlaylistsDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsRepository
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

class PlaylistsRepositoryImpl(appDatabase: AppDatabase) : PlaylistsRepository {
    private val dao: PlaylistsDao = appDatabase.getPlaylistsDao()

    override fun addPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlistEntity = playlist.toEntity())
    }

    override fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        val mutableList = mutableListOf<Int>()
        mutableList.addAll(playlist.trackIds)
        mutableList.add(track.trackId)
        dao.updatePlaylist(
            playlistEntity = playlist.copy(trackIds = mutableList).toEntity()
        )
    }

    override fun getPlaylists(): List<Playlist> {
        return dao.getPlaylists().map { playlistEntity -> playlistEntity.toDomain() }
    }
}
