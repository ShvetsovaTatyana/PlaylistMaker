package com.github.ilyashvetsov.playlistmaker

import android.content.res.Resources.getSystem
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Locale

class AudioPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)
        val buttonBackArrow = findViewById<ImageView>(R.id.button_back_arrow)
        buttonBackArrow.setOnClickListener {
            finish()
        }
        val picture = findViewById<ImageView>(R.id.place_holder_empty_image)
        val nameSing = findViewById<TextView>(R.id.name_sing)
        val nameSinger = findViewById<TextView>(R.id.name_singer)
        val addButton = findViewById<ImageButton>(R.id.add_button)
        val reproduceButton = findViewById<ImageButton>(R.id.reproduce_button)
        val likeButton = findViewById<ImageButton>(R.id.like_button)
        val timeSing = findViewById<TextView>(R.id.time_sing)
        val album = findViewById<TextView>(R.id.album)
        val durationSing = findViewById<TextView>(R.id.duration_sing)
        val albumSing = findViewById<TextView>(R.id.album_sing)
        val yearSing = findViewById<TextView>(R.id.year_sing)
        val genreSing = findViewById<TextView>(R.id.genre_sing)
        val countrySing = findViewById<TextView>(R.id.country_sing)

        val trackExtra = intent.getStringExtra(TRACK_KEY_AUDIO_PLAYER)
        val trackJsonExtra = Gson().fromJson(trackExtra, Track::class.java)

        Glide.with(picture)
            .load(trackJsonExtra.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .transform(CenterCrop(), RoundedCorners(8.px))
            .into(picture)
        nameSing.text = trackJsonExtra.trackName
        nameSinger.text = trackJsonExtra.artistName
        timeSing.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackJsonExtra.trackTimeMillis)
        durationSing.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackJsonExtra.trackTimeMillis)

        if (!trackJsonExtra.collectionName.isNullOrBlank()) {
            albumSing.text = trackJsonExtra.collectionName
        } else {
            album.visibility = View.GONE
            albumSing.visibility = View.GONE
        }

        val localDate = trackJsonExtra.releaseDate
            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        yearSing.text = localDate.year.toString()

        genreSing.text = trackJsonExtra.primaryGenreName
        countrySing.text = trackJsonExtra.country
    }
}

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
