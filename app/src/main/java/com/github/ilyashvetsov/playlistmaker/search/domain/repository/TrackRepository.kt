package com.github.ilyashvetsov.playlistmaker.search.domain.repository

import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track

interface TrackRepository {
    fun searchTracks(expression: String): List<Track>
}
