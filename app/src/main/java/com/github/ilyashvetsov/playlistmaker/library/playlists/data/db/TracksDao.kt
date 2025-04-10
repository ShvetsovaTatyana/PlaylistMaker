package com.github.ilyashvetsov.playlistmaker.library.playlists.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Delete(entity = TrackEntity::class)
    suspend fun deleteTrack(trackEntity: TrackEntity)

    @Query("SELECT * FROM tracks_table WHERE trackId IN (:trackIds)")
    fun getTracks(trackIds: List<Int>): Flow<List<TrackEntity>>
}
