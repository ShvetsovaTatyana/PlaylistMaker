package com.github.ilyashvetsov.playlistmaker.search.domain

import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.SearchHistoryRepository

interface SearchHistoryInteractor {
    fun saveTrack(track: Track)
    fun getTrackList(): List<Track>
    fun clearTrackList()
}

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
): SearchHistoryInteractor {

    override fun saveTrack(track: Track) {
        repository.saveTrack(track)
    }

    override fun getTrackList(): List<Track> {
        return repository.getTrackList()
    }

    override fun clearTrackList() {
        repository.clearTrackList()
    }
}
