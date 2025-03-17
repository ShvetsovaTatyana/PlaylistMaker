package com.github.ilyashvetsov.playlistmaker.search.data.network

import com.github.ilyashvetsov.playlistmaker.search.data.dto.Response

class RetrofitNetworkClient(
    private val itunesApiService: ItunesApiService
) : NetworkClient {
    override suspend fun getTrackList(term: String): Response {
        return try {
            val resp = itunesApiService.getTrackList(term)
            resp.apply { resultCode = 200 }
        } catch (e: Throwable) {
            Response().apply { resultCode = 500 }
        }
    }
}
