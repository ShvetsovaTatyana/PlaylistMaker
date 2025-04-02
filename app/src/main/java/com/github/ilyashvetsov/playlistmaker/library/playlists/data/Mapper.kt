package com.github.ilyashvetsov.playlistmaker.library.playlists.data

import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.PlaylistEntity
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
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
