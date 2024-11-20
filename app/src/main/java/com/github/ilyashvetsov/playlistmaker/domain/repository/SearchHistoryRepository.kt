package com.github.ilyashvetsov.playlistmaker.domain.repository

import com.github.ilyashvetsov.playlistmaker.domain.models.Track

interface SearchHistoryRepository {
    fun saveTrack(track: Track)
    fun getTrackList(): ArrayList<Track>
    fun deleteTrackList()
}
