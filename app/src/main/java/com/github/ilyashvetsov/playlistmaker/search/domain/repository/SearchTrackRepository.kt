package com.github.ilyashvetsov.playlistmaker.search.domain.repository

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchTrackRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
}
