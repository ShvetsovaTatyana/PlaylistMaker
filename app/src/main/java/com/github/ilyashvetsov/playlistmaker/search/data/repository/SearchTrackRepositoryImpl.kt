package com.github.ilyashvetsov.playlistmaker.search.data.repository

import com.github.ilyashvetsov.playlistmaker.search.data.dto.TrackListResponse
import com.github.ilyashvetsov.playlistmaker.search.data.network.NetworkClient
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.SearchTrackRepository
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchTrackRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchTrackRepository {
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> =
        flow {
            val response = networkClient.getTrackList(expression)
            when (response.resultCode) {
                200 -> {
                    val data = (response as TrackListResponse).results
                    emit(Resource.Success(data))
                }

                else -> {
                    emit(Resource.Error("Ошибка сервера"))
                }
            }
        }
}
