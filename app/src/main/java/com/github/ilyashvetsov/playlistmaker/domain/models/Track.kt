package com.github.ilyashvetsov.playlistmaker.domain.models

import java.util.Date

class Track(
    val trackName: String,
    val artistName: String,
    val artworkUrl100: String,
    val trackTimeMillis: Int,
    val collectionName: String?,
    val releaseDate: Date,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String
)
