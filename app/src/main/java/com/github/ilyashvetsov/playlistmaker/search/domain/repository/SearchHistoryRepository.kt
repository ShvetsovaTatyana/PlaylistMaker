package com.github.ilyashvetsov.playlistmaker.search.domain.repository

import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track

interface SearchHistoryRepository {
    fun saveTrack(track: Track)
    fun getTrackList(): List<Track>
    fun clearTrackList()
}
