package com.github.ilyashvetsov.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(private val sharedPrefs: SharedPreferences) {
    private val gson = Gson()

    fun saveTrack(track: Track) {
        val trackListJson = sharedPrefs.getString(TRACK_KEY, "") ?: ""
        val trackList = if (trackListJson.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Track>>() {}.type
            gson.fromJson<ArrayList<Track>>(trackListJson, listType)
        } else {
            arrayListOf()
        }
        trackList.add(0, track)
        if (trackList.size > 10) {
            trackList.removeAt(10)
        }
        val newTrackListJson = gson.toJson(trackList)
        sharedPrefs.edit().putString(TRACK_KEY, newTrackListJson).apply()
    }

    fun getTrackList(): ArrayList<Track> {
        val getListJson = sharedPrefs.getString(TRACK_KEY, "") ?: ""
        return if (getListJson.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Track>>() {}.type
            Gson().fromJson(getListJson, listType)
        } else {
            arrayListOf()
        }
    }

    fun deleteTrackList() {
        sharedPrefs.edit().clear().apply()
    }

    companion object {
        private const val TRACK_KEY = "track_key"
    }
}
