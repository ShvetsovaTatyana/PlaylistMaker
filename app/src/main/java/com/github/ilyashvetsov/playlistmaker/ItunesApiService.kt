package com.github.ilyashvetsov.playlistmaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    fun getTrackList(@Query("term") text: String): Call<ListTrackResponse>
}