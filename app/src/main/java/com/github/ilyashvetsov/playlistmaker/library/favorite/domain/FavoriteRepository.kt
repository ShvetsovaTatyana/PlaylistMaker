package com.github.ilyashvetsov.playlistmaker.library.favorite.domain

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addTrack(track: Track)
    suspend fun removeTrack(track: Track)
    fun getTracks(): Flow<List<Track>>
    suspend fun getTrackById(trackId: Int): Track?
}
