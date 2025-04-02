package com.github.ilyashvetsov.playlistmaker.library.favorite.domain

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

interface FavoriteInteractor {
    fun addTrack(track: Track)
    fun removeTrack(track: Track)
    fun getTracks(): List<Track>
    fun isFavorite(track: Track): Boolean
}

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
): FavoriteInteractor {
    override fun addTrack(track: Track) {
        repository.addTrack(track)
    }

    override fun removeTrack(track: Track) {
        repository.removeTrack(track)
    }

    override fun getTracks(): List<Track> {
        return repository.getTracks()
    }

    override fun isFavorite(track: Track): Boolean {
        return repository.getTrackById(track.trackId) != null
    }
}
