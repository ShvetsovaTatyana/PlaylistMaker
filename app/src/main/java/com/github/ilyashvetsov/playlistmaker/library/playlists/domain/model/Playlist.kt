package com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model

data class Playlist(
    val id: Int,
    val name: String,
    val description: String,
    val imagePath: String?,
    val trackIds: List<Int>,
)
