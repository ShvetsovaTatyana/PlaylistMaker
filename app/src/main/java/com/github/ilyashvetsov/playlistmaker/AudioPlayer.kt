package com.github.ilyashvetsov.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.text.SimpleDateFormat
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
        val durationSing = findViewById<TextView>(R.id.duration_sing)
        val albumSing = findViewById<TextView>(R.id.album_sing)
        val yearSing = findViewById<TextView>(R.id.year_sing)
        val genreSing = findViewById<TextView>(R.id.genre_sing)
        val countrySing = findViewById<TextView>(R.id.country_sing)
        val trackExtra = intent.getStringExtra(TRACK_KEY_AUDIO_PLAYER)
        val trackJsonExtra = Gson().fromJson(trackExtra, Track::class.java)
        Glide.with(picture).load(trackJsonExtra.artworkUrl100)
            .placeholder(R.drawable.placeholder).into(picture)
        nameSing.text = trackJsonExtra.trackName
        nameSinger.text = trackJsonExtra.artistName
        durationSing.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackJsonExtra.trackTimeMillis)
        albumSing.text = trackJsonExtra.collectionName
        yearSing.text = trackJsonExtra.releaseDate
        genreSing.text = trackJsonExtra.primaryGenreName
        countrySing.text = trackJsonExtra.country
    }
}