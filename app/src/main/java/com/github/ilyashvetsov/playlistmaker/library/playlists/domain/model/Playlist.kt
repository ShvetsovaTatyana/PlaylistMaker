package com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist(
    val id: Int,
    val name: String,
    val description: String,
    val imagePath: String?,
    val trackIds: List<Int>,
) : Parcelable
