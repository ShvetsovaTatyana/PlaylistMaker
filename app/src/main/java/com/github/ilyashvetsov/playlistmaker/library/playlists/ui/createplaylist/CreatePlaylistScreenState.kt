package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.createplaylist

data class CreatePlaylistScreenState(
    val name: String,
    val description: String,
    val imagePath: String?,
) {
    val isButtonEnabled: Boolean
        get() = name.isNotEmpty()

    fun isEmpty(): Boolean = this == EMPTY

    fun isNotEmpty(): Boolean = isEmpty().not()

    companion object {
        val EMPTY = CreatePlaylistScreenState(
            name = "",
            description = "",
            imagePath = null,
        )
    }
}
