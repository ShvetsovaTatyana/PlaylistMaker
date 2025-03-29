package com.github.ilyashvetsov.playlistmaker.track.presentation

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.TrackItemBinding
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(
    private val binding: TrackItemBinding,
    private val onItemClickListener: (track: Track) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Track) = with(binding) {
        Glide.with(pictureImageView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .into(pictureImageView)
        nameSing.text = model.trackName
        nameSinger.text = model.artistName
        timeSing.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        itemView.setOnClickListener { onItemClickListener.invoke(model) }
    }
}
