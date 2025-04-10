package com.github.ilyashvetsov.playlistmaker.search.data.dto

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

class TrackListResponse(
    val resultCount: Int,
    val results: List<Track>
) : Response()
