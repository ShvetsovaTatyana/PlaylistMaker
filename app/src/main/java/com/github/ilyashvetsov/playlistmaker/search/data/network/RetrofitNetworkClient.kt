package com.github.ilyashvetsov.playlistmaker.search.data.network

import com.github.ilyashvetsov.playlistmaker.search.data.dto.Response

class RetrofitNetworkClient(
    private val itunesApiService: ItunesApiService
) : NetworkClient {
    override fun getTrackList(term: String): Response {
        val resp = itunesApiService.getTrackList(term).execute()
        val body = resp.body() ?: Response()
        return body.apply { resultCode = resp.code() }
    }
}
