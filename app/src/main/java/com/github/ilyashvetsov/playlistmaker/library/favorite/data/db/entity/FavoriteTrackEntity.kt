package com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorite_table")
data class FavoriteTrackEntity(
    @PrimaryKey
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val artworkUrl100: String,
    val trackTimeMillis: Int,
    val collectionName: String?,
    val releaseDate: Date,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    val createdAt: Date = Date(),
)
