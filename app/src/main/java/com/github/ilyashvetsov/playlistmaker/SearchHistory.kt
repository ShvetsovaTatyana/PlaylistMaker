package com.github.ilyashvetsov.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val TRACK_KEY = "track_key"

class SearchHistory(val sharedPrefs: SharedPreferences) {
    fun saveTrack(track: Track) {
        val gson = Gson()
        val trackListJson = sharedPrefs.getString(TRACK_KEY, "") ?: ""
        val trackList = if (trackListJson.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Track>>() {}.type
            gson.fromJson<ArrayList<Track>>(trackListJson, listType)
        } else {
            arrayListOf()
        }
        trackList.add(0, track)
        if (trackList.size <= 10) {
        } else
            trackList.removeAt(10)
        val newTrackListJson = gson.toJson(trackList)
        sharedPrefs.edit().putString(TRACK_KEY, newTrackListJson).apply()
    }

    fun getTrackList(): ArrayList<Track> {
        val gson = Gson()
        val getListJson = sharedPrefs.getString(TRACK_KEY, "") ?: ""
        if (getListJson.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Track>>() {}.type
            val trackList = gson.fromJson<ArrayList<Track>>(getListJson, listType)
            return trackList
        } else {
            return arrayListOf()
        }
    }

    fun deleteTrackList() {
        sharedPrefs.edit().clear().apply()
    }
}

