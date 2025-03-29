package com.github.ilyashvetsov.playlistmaker.library.favorite.domain

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

interface FavoriteRepository {
    fun addTrack(track: Track)
    fun removeTrack(track: Track)
    fun getTracks(): List<Track>
    fun getTrackById(trackId: Int): Track?
}
