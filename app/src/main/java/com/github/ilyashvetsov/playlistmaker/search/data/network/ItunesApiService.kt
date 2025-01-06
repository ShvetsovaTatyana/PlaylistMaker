package com.github.ilyashvetsov.playlistmaker.search.data.network

import com.github.ilyashvetsov.playlistmaker.search.data.dto.TrackListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    fun getTrackList(@Query("term") term: String): Call<TrackListResponse>
}
