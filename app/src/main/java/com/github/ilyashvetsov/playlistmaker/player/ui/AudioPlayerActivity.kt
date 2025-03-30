package com.github.ilyashvetsov.playlistmaker.player.ui

import android.content.res.Resources.getSystem
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel.Companion.STATE_PAUSED
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel.Companion.STATE_PLAYING
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel.Companion.STATE_PREPARED
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private val viewModel by viewModel<AudioPlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val track = intent.getParcelableExtra<Track>(TRACK_KEY)
            ?: throw Exception("track is null")

        viewModel.init(track)

        with(binding) {
            nameSing.text = track.trackName
            nameSinger.text = track.artistName
            genreSing.text = track.primaryGenreName
            countrySing.text = track.country
            durationSing.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

            if (!track.collectionName.isNullOrBlank()) {
                albumSing.text = track.collectionName
            } else {
                album.visibility = View.GONE
                albumSing.visibility = View.GONE
            }

            val localDate = track.releaseDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            yearSing.text = localDate.year.toString()

            Glide.with(placeHolderEmptyImage)
                .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .placeholder(R.drawable.placeholder)
                .transform(CenterCrop(), RoundedCorners(8.px))
                .into(placeHolderEmptyImage)

            buttonBackArrow.setOnClickListener { finish() }
            playButton.setOnClickListener { viewModel.playbackControl() }
            likeButton.setOnClickListener { viewModel.addTrackToFavorite(track) }

            viewModel.timeSing.observe(this@AudioPlayerActivity) { timeSing.text = it }
            viewModel.isFavoriteState.observe(this@AudioPlayerActivity) { isFavorite ->
                likeButton.setImageResource(
                    if (isFavorite == true) {
                        R.drawable.favorite
                    } else {
                        R.drawable.like
                    }
                )
            }
            viewModel.playerState.observe(this@AudioPlayerActivity) { state ->
                when (state) {
                    STATE_PREPARED -> {
                        playButton.isEnabled = true
                        playButton.setImageResource(R.drawable.play)
                    }

                    STATE_PLAYING -> {
                        playButton.setImageResource(R.drawable.pause)
                    }

                    STATE_PAUSED -> {
                        playButton.setImageResource(R.drawable.play)
                    }
                }
            }
        }

        viewModel.preparePlayer(track.previewUrl)
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

    override fun onDestroy() {
        viewModel.releasePlayer()
        super.onDestroy()
    }

    companion object {
        const val TRACK_KEY = "track_key"

        // TODO use TypedValue.applyDimension
        private val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
    }
}
