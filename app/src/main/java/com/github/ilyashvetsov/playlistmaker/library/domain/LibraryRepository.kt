package com.github.ilyashvetsov.playlistmaker.library.domain

import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

interface LibraryRepository {
    fun addTrack(track: Track)
    fun removeTrack(track: Track)
    fun getTracks(): List<Track>
    fun getTrackById(trackId: Int): Track?
}
