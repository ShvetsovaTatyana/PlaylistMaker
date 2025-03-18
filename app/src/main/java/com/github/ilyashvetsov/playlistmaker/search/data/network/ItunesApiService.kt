package com.github.ilyashvetsov.playlistmaker.search.data.network

import com.github.ilyashvetsov.playlistmaker.search.data.dto.TrackListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    suspend fun getTrackList(@Query("term") term: String): TrackListResponse
}
