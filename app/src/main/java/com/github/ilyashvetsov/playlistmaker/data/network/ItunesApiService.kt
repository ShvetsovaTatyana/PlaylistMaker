package com.github.ilyashvetsov.playlistmaker.data.network

import com.github.ilyashvetsov.playlistmaker.data.dto.TrackListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    fun getTrackList(@Query("term") term: String): Call<TrackListResponse>
}
