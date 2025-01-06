package com.github.ilyashvetsov.playlistmaker.search.domain.model

import java.util.Date

class Track(
    val trackId: Int,
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
