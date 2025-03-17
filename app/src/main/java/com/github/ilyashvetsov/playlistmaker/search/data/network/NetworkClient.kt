package com.github.ilyashvetsov.playlistmaker.search.data.network

import com.github.ilyashvetsov.playlistmaker.search.data.dto.Response

interface NetworkClient {
    suspend fun getTrackList(term: String): Response
}
