package com.github.ilyashvetsov.playlistmaker.data.dto

import com.github.ilyashvetsov.playlistmaker.domain.models.Track

class TrackListResponse(
    val resultCount: Int,
    val results: List<Track>
) : Response()
