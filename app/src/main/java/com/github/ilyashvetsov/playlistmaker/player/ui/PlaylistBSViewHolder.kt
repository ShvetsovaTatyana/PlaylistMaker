package com.github.ilyashvetsov.playlistmaker.player.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.PlatlistBsItemBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import java.io.File

class PlaylistBSViewHolder(
    private val binding: PlatlistBsItemBinding,
    private val onItemClickListener: (Playlist) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Playlist) = with(binding) {
        Glide.with(pictureImageView)
            .load(model.imagePath?.let { File(it) })
            .placeholder(R.drawable.placeholder)
            .into(pictureImageView)
        playlistName.text = model.name
        tracksCount.text = model.trackIds.size.toString()
        itemView.setOnClickListener { onItemClickListener.invoke(model) }
    }
}
