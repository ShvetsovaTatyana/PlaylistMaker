package com.github.ilyashvetsov.playlistmaker.library.playlists.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {

    @Insert(entity = PlaylistEntity::class)
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Update(entity = PlaylistEntity::class)
    suspend fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Delete(entity = PlaylistEntity::class)
    suspend fun deletePlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlists_table")
    fun getPlaylists(): Flow<List<PlaylistEntity>>
}
