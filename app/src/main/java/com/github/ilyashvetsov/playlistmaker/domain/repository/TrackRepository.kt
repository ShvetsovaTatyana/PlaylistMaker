package com.github.ilyashvetsov.playlistmaker.domain.repository

import com.github.ilyashvetsov.playlistmaker.domain.models.Track

interface TrackRepository {
    fun searchTracks(expression: String): List<Track>
}
