package com.github.ilyashvetsov.playlistmaker.library.favorite.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.entity.TrackEntity

@Dao
interface FavoriteDao {

    @Insert(entity = TrackEntity::class)
    fun insertTrack(trackEntity: TrackEntity)

    @Delete(entity = TrackEntity::class)
    fun deleteTrack(trackEntity: TrackEntity)

    @Query("SELECT * FROM library_table ORDER BY createdAt DESC")
    fun getTracks(): List<TrackEntity>

    @Query("SELECT * FROM library_table WHERE trackId = :trackId")
    fun getTrackById(trackId: Int): List<TrackEntity>
}
