package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.PlaylistItemBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.util.px
import java.io.File

class PlaylistViewHolder(
    private val binding: PlaylistItemBinding,
    private val onItemClickListener: (Playlist) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Playlist) = with(binding) {
        Glide.with(playlistImage)
            .load(model.imagePath?.let { File(it) })
            .placeholder(R.drawable.placeholder)
            .transform(CenterCrop(), RoundedCorners(8.px))
            .into(playlistImage)
        playlistName.text = model.name
        tracksCount.text = "Треков: ${model.trackIds.size}"
        itemView.setOnClickListener { onItemClickListener.invoke(model) }
    }
}
