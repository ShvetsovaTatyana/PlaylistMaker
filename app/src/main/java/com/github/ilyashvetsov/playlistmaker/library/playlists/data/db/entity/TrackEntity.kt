package com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tracks_table")
data class TrackEntity(
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
