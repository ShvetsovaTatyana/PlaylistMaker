package com.github.ilyashvetsov.playlistmaker.data.network

import com.github.ilyashvetsov.playlistmaker.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitNetworkClient : NetworkClient {
    private val itunesBaseUrl = "https://itunes.apple.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesApiService = retrofit.create<ItunesApiService>()

    override fun getTrackList(term: String): Response {
        val resp = itunesApiService.getTrackList(term).execute()
        val body = resp.body() ?: Response()
        return body.apply { resultCode = resp.code() }
    }
}
