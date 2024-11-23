package com.github.ilyashvetsov.playlistmaker.domain.interactors

import com.github.ilyashvetsov.playlistmaker.domain.models.Track
import com.github.ilyashvetsov.playlistmaker.domain.repository.SearchHistoryRepository

interface SearchHistoryInteractor {
    fun saveTrack(track: Track)
    fun getTrackList(): ArrayList<Track>
    fun deleteTrackList()
}

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
): SearchHistoryInteractor {

    override fun saveTrack(track: Track) {
        repository.saveTrack(track)
    }

    override fun getTrackList(): ArrayList<Track> {
        return repository.getTrackList()
    }

    override fun deleteTrackList() {
        repository.deleteTrackList()
    }
}
