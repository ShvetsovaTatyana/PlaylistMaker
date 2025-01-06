package com.github.ilyashvetsov.playlistmaker.search.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.search.domain.repository.SearchHistoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryRepositoryImpl(
    private val sharedPrefs: SharedPreferences
): SearchHistoryRepository {
    private val gson = Gson()

    override fun saveTrack(track: Track) {
        val trackList = getTrackList()
        trackList.removeIf { it.trackId == track.trackId }
        trackList.add(0, track)
        if (trackList.size > 10) {
            trackList.removeAt(10)
        }
        val trackListJson = gson.toJson(trackList)
        sharedPrefs.edit { putString(TRACK_KEY, trackListJson) }
    }

    override fun getTrackList(): ArrayList<Track> {
        val trackListJson = sharedPrefs.getString(TRACK_KEY, "") ?: ""
        return if (trackListJson.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Track>>() {}.type
            Gson().fromJson(trackListJson, listType)
        } else {
            arrayListOf()
        }
    }

    override fun clearTrackList() {
        sharedPrefs.edit { clear() }
    }

    companion object {
        private const val TRACK_KEY = "track_key"
    }
}
