package com.github.ilyashvetsov.playlistmaker.library.data

import com.github.ilyashvetsov.playlistmaker.library.data.db.entity.TrackEntity
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

fun Track.toEntity(): TrackEntity =
    TrackEntity(
        trackId = trackId,
        trackName = trackName,
        artistName = artistName,
        artworkUrl100 = artworkUrl100,
        trackTimeMillis = trackTimeMillis,
        collectionName = collectionName,
        releaseDate = releaseDate,
        primaryGenreName = primaryGenreName,
        country = country,
        previewUrl = previewUrl
    )

fun TrackEntity.toDomain(): Track =
    Track(
        trackId = trackId,
        trackName = trackName,
        artistName = artistName,
        artworkUrl100 = artworkUrl100,
        trackTimeMillis = trackTimeMillis,
        collectionName = collectionName,
        releaseDate = releaseDate,
        primaryGenreName = primaryGenreName,
        country = country,
        previewUrl = previewUrl
    )
