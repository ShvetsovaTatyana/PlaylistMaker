package com.github.ilyashvetsov.playlistmaker.library.playlists.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.PlaylistEntity

@Dao
interface PlaylistsDao {

    @Insert(entity = PlaylistEntity::class)
    fun insertPlaylist(playlistEntity: PlaylistEntity)

//    TODO
//    fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlists_table")
    fun getPlaylists(): List<PlaylistEntity>
}
