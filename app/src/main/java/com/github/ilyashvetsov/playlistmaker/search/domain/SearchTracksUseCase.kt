package com.github.ilyashvetsov.playlistmaker.search.domain

import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.TrackRepository
import java.util.concurrent.Executors

class SearchTracksUseCase(private val trackRepository: TrackRepository) {
    private val executor = Executors.newCachedThreadPool()

    fun execute(
        expression: String,
        onSuccess: (ArrayList<Track>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        executor.execute {
            try {
                val tracks = trackRepository.searchTracks(expression)
                onSuccess(tracks)
            } catch (e: Throwable) {
                onFailure(e)
            }
        }
    }
}
