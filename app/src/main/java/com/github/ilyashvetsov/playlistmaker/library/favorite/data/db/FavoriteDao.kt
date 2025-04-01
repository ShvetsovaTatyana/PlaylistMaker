package com.github.ilyashvetsov.playlistmaker.library.favorite.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.entity.FavoriteTrackEntity

@Dao
interface FavoriteDao {

    @Insert(entity = FavoriteTrackEntity::class)
    fun insertTrack(favoriteTrackEntity: FavoriteTrackEntity)

    @Delete(entity = FavoriteTrackEntity::class)
    fun deleteTrack(favoriteTrackEntity: FavoriteTrackEntity)

    @Query("SELECT * FROM favorite_table ORDER BY createdAt DESC")
    fun getTracks(): List<FavoriteTrackEntity>

    @Query("SELECT * FROM favorite_table WHERE trackId = :trackId")
    fun getTrackById(trackId: Int): List<FavoriteTrackEntity>
}
