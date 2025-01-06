package com.github.ilyashvetsov.playlistmaker.search.data.dto

import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track

class TrackListResponse(
    val resultCount: Int,
    val results: ArrayList<Track>
) : Response()
