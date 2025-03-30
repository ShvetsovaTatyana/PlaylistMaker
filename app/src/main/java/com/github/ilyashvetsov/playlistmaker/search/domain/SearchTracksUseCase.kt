package com.github.ilyashvetsov.playlistmaker.search.domain

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.SearchTrackRepository
import com.github.ilyashvetsov.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchTracksUseCase(private val trackRepository: SearchTrackRepository) {

    fun execute(expression: String): Flow<Pair<List<Track>?, String?>> {
        return trackRepository.searchTracks(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
