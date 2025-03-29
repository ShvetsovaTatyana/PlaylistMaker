package com.github.ilyashvetsov.playlistmaker.library.playlists.data

import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.PlaylistsDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.PlaylistsRepository
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase

class PlaylistsRepositoryImpl(appDatabase: AppDatabase) : PlaylistsRepository {
    private val dao: PlaylistsDao = appDatabase.getPlaylistsDao()

    override fun addPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlistEntity = playlist.toEntity())
    }

    override fun getPlaylists(): List<Playlist> {
        return dao.getPlaylists().map { playlistEntity -> playlistEntity.toDomain() }
    }
}
