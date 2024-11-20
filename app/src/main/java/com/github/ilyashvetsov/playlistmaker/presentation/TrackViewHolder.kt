package com.github.ilyashvetsov.playlistmaker.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(
    parentView: View,
    private val onItemClickListener: (track: Track) -> Unit
) : RecyclerView.ViewHolder(parentView) {

    private val pictureImageView: ImageView = parentView.findViewById(R.id.picture_imageView)
    private val nameSingTextView: TextView = parentView.findViewById(R.id.name_sing)
    private val singerTextView: TextView = parentView.findViewById(R.id.name_singer)
    private val timeSingTextView: TextView = parentView.findViewById(R.id.time_sing)

    fun bind(model: Track) {
        Glide.with(pictureImageView).load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder).into(pictureImageView)
        nameSingTextView.text = model.trackName
        singerTextView.text = model.artistName
        timeSingTextView.text = SimpleDateFormat("mm:ss", Locale.getDefault())
            .format(model.trackTimeMillis)
        itemView.setOnClickListener {
            onItemClickListener.invoke(model)
        }
    }
}
