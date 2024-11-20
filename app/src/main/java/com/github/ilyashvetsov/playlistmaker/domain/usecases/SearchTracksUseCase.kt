package com.github.ilyashvetsov.playlistmaker.domain.usecases

import com.github.ilyashvetsov.playlistmaker.domain.models.Track
import com.github.ilyashvetsov.playlistmaker.domain.repository.TrackRepository
import java.util.concurrent.Executors

class SearchTracksUseCase(private val trackRepository: TrackRepository) {
    private val executor = Executors.newCachedThreadPool()

    fun execute(
        expression: String,
        onSuccess: (List<Track>) -> Unit,
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
