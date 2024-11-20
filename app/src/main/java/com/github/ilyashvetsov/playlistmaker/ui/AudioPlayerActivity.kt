package com.github.ilyashvetsov.playlistmaker.ui

import android.content.res.Resources.getSystem
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.domain.models.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var playButton: ImageButton
    private lateinit var timeSing: TextView

    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timeSing.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                .format(mediaPlayer.currentPosition)
            handler.postDelayed(this, RUNNABLE_DELAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        val buttonBackArrow = findViewById<ImageView>(R.id.button_back_arrow)
        val picture = findViewById<ImageView>(R.id.place_holder_empty_image)
        val nameSing = findViewById<TextView>(R.id.name_sing)
        val nameSinger = findViewById<TextView>(R.id.name_singer)
        val addButton = findViewById<ImageButton>(R.id.add_button)
        playButton = findViewById(R.id.play_button)
        val likeButton = findViewById<ImageButton>(R.id.like_button)
        timeSing = findViewById(R.id.time_sing)
        val album = findViewById<TextView>(R.id.album)
        val durationSing = findViewById<TextView>(R.id.duration_sing)
        val albumSing = findViewById<TextView>(R.id.album_sing)
        val yearSing = findViewById<TextView>(R.id.year_sing)
        val genreSing = findViewById<TextView>(R.id.genre_sing)
        val countrySing = findViewById<TextView>(R.id.country_sing)

        val trackJson = intent.getStringExtra(TRACK_KEY)
        val track = Gson().fromJson(trackJson, Track::class.java)

        Glide.with(picture)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .transform(CenterCrop(), RoundedCorners(8.px))
            .into(picture)
        nameSing.text = track.trackName
        nameSinger.text = track.artistName
        durationSing.text = SimpleDateFormat("mm:ss", Locale.getDefault())
            .format(track.trackTimeMillis)

        if (!track.collectionName.isNullOrBlank()) {
            albumSing.text = track.collectionName
        } else {
            album.visibility = View.GONE
            albumSing.visibility = View.GONE
        }

        val localDate = track.releaseDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        yearSing.text = localDate.year.toString()

        genreSing.text = track.primaryGenreName
        countrySing.text = track.country

        buttonBackArrow.setOnClickListener { finish() }
        playButton.setOnClickListener { playbackControl() }

        preparePlayer(track.previewUrl)
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
            playButton.setImageResource(R.drawable.play)
            handler.removeCallbacks(runnable)
            timeSing.text = "00:00"
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
        playButton.setImageResource(R.drawable.pause)
        handler.postDelayed(runnable, RUNNABLE_DELAY)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        playButton.setImageResource(R.drawable.play)
        handler.removeCallbacks(runnable)
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    companion object {
        const val TRACK_KEY = "track_key"

        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3

        private const val RUNNABLE_DELAY = 300L

        private val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
    }
}
