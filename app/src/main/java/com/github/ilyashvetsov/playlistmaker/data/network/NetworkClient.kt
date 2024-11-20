package com.github.ilyashvetsov.playlistmaker.data.network

import com.github.ilyashvetsov.playlistmaker.data.dto.Response

interface NetworkClient {
    fun getTrackList(term: String): Response
}
