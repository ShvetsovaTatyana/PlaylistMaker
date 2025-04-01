package com.github.ilyashvetsov.playlistmaker.library.favorite.data

import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.entity.FavoriteTrackEntity
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

fun Track.toFavoriteTrackEntity(): FavoriteTrackEntity =
    FavoriteTrackEntity(
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

fun FavoriteTrackEntity.toDomain(): Track =
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
