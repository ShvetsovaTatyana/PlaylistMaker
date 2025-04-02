package com.github.ilyashvetsov.playlistmaker.library.favorite.domain

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteInteractor {
    suspend fun addTrack(track: Track)
    suspend fun removeTrack(track: Track)
    fun getTracks(): Flow<List<Track>>
    suspend fun isFavorite(track: Track): Boolean
}

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
): FavoriteInteractor {
    override suspend fun addTrack(track: Track) {
        repository.addTrack(track)
    }

    override suspend fun removeTrack(track: Track) {
        repository.removeTrack(track)
    }

    override fun getTracks(): Flow<List<Track>> {
        return repository.getTracks()
    }

    override suspend fun isFavorite(track: Track): Boolean {
        return repository.getTrackById(track.trackId) != null
    }
}
