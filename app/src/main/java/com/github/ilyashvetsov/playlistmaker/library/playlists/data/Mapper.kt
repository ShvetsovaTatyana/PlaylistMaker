package com.github.ilyashvetsov.playlistmaker.library.playlists.data

import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.PlaylistEntity
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.TrackEntity
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Playlist.toEntity(): PlaylistEntity =
    PlaylistEntity(
        id = id,
        name = name,
        description = description,
        imagePath = imagePath,
        trackIdsJson = Gson().toJson(trackIds),
        tracksCount = trackIds.size
    )

fun PlaylistEntity.toDomain(): Playlist =
    Playlist(
        id = id,
        name = name,
        description = description,
        imagePath = imagePath,
        trackIds = Gson().fromJson(trackIdsJson, object : TypeToken<List<Int>>() {}.type)
    )

fun Track.toTrackEntity(): TrackEntity =
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
