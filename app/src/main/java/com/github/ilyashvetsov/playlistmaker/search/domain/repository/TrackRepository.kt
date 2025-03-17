package com.github.ilyashvetsov.playlistmaker.search.domain.repository

import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
}
