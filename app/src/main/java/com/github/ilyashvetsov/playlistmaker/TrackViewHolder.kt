package com.github.ilyashvetsov.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(parentView: View, private val onItemClickListener: (track: Track) -> Unit) :
    RecyclerView.ViewHolder(parentView) {
    private val pictureImageView: ImageView
    private val nameSingTextView: TextView
    private val singerTextView: TextView
    private val timeSingTextView: TextView

    init {
        pictureImageView = parentView.findViewById(R.id.picture_imageView)
        nameSingTextView = parentView.findViewById(R.id.name_sing)
        singerTextView = parentView.findViewById(R.id.name_singer)
        timeSingTextView = parentView.findViewById(R.id.time_sing)
    }

    fun bind(model: Track) {
        Glide.with(pictureImageView).load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder).into(pictureImageView)
        nameSingTextView.text = model.trackName
        singerTextView.text = model.artistName
        timeSingTextView.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        itemView.setOnClickListener {
            onItemClickListener.invoke(model)
        }
    }
}
