package com.github.ilyashvetsov.playlistmaker.data.repository

import com.github.ilyashvetsov.playlistmaker.data.dto.TrackListResponse
import com.github.ilyashvetsov.playlistmaker.data.network.NetworkClient
import com.github.ilyashvetsov.playlistmaker.domain.repository.TrackRepository
import com.github.ilyashvetsov.playlistmaker.domain.models.Track

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.getTrackList(expression)
        return if (response.resultCode == 200) {
            (response as TrackListResponse).results
        } else {
            emptyList()
        }
    }
}
