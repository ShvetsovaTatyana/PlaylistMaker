package com.github.ilyashvetsov.playlistmaker.search.data.repository

import com.github.ilyashvetsov.playlistmaker.search.data.dto.TrackListResponse
import com.github.ilyashvetsov.playlistmaker.search.data.network.NetworkClient
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.TrackRepository
import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {
    override fun searchTracks(expression: String): ArrayList<Track> {
        val response = networkClient.getTrackList(expression)
        return if (response.resultCode == 200) {
            (response as TrackListResponse).results
        } else {
            arrayListOf()
        }
    }
}
