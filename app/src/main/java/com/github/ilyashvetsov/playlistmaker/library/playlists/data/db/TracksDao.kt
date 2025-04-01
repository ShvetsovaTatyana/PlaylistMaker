package com.github.ilyashvetsov.playlistmaker.library.playlists.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.TrackEntity

@Dao
interface TracksDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertTrack(trackEntity: TrackEntity)

    @Query("SELECT * FROM tracks_table WHERE trackId IN (:trackIds)")
    fun getTracks(trackIds: List<Int>): List<TrackEntity>
}
