package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.databinding.PlaylistItemBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist

class AdapterPlaylist(
    private val onItemClickListener: (Playlist) -> Unit
) : RecyclerView.Adapter<PlaylistViewHolder>() {
    var playlists: List<Playlist> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val playlistItemBinding = PlaylistItemBinding.inflate(layoutInflater, parent, false)
        return PlaylistViewHolder(playlistItemBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}
