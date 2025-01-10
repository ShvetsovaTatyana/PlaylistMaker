package com.github.ilyashvetsov.playlistmaker.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.databinding.TrackItemBinding
import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track

class AdapterTrack(
    private val onItemClickListener: (track: Track) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {
    var trackList: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val trackItemBinding = TrackItemBinding.inflate(layoutInflater, parent, false)
        return TrackViewHolder(trackItemBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
}
