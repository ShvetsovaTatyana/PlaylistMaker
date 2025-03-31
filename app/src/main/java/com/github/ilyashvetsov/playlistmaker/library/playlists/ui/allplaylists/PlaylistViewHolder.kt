package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.PlaylistItemBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import java.io.File

class PlaylistViewHolder(
    private val binding: PlaylistItemBinding,
    private val onItemClickListener: (Playlist) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Playlist) = with(binding) {
        Glide.with(playlistImage)
            .load(model.imagePath?.let { File(it) })
            .placeholder(R.drawable.placeholder)
            .into(playlistImage)
        playlistName.text = model.name
        tracksCount.text = model.trackIds.size.toString()
        itemView.setOnClickListener { onItemClickListener.invoke(model) }
    }
}
