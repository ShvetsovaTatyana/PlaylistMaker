package com.github.ilyashvetsov.playlistmaker.player.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.databinding.PlatlistBsItemBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist

class AdapterBSPlaylist(
    private val onItemClickListener: (Playlist) -> Unit
) : RecyclerView.Adapter<PlaylistBSViewHolder>() {
    var playlists: List<Playlist> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistBSViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val playlistBSItemBinding = PlatlistBsItemBinding.inflate(layoutInflater, parent, false)
        return PlaylistBSViewHolder(playlistBSItemBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: PlaylistBSViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}
