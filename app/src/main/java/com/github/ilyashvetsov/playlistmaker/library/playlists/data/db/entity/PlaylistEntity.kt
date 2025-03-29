package com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val imageUri: String,
    val trackIdsJson: String,
    val tracksCount: Int,
)
