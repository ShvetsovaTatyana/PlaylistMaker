package com.github.ilyashvetsov.playlistmaker.player.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentAudioPlayerBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel.Companion.STATE_PAUSED
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel.Companion.STATE_PLAYING
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerViewModel.Companion.STATE_PREPARED
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.util.px
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Locale

class AudioPlayerFragment : Fragment() {
    private lateinit var binding: FragmentAudioPlayerBinding
    private val viewModel by viewModel<AudioPlayerViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private var isPlaylistClickAllowed = true
    private val adapter: AdapterBSPlaylist by lazy {
        AdapterBSPlaylist { playlist ->
            if (playlistClickDebounce()) {
                val isAdded = viewModel.onPlaylistClicked(playlist)
                if (isAdded) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    Toast.makeText(
                        requireContext(),
                        "Добавлено в плейлист ${playlist.name}",
                        Toast.LENGTH_LONG,
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Трек уже добавлен в плейлист ${playlist.name}",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val track = requireArguments().getParcelable<Track>(TRACK_KEY)
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

            buttonBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            addButton.setOnClickListener { showBottomSheet() }
            playButton.setOnClickListener { viewModel.playbackControl() }
            likeButton.setOnClickListener { viewModel.addTrackToFavorite() }

            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
            }

            bottomSheetBehavior.addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        overlay.visibility = when (newState) {
                            BottomSheetBehavior.STATE_HIDDEN -> View.GONE
                            else -> View.VISIBLE
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                }
            )

            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

            buttonNewPlaylist.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_audio_player_to_navigation_create_playlist)
            }

            viewModel.timeSing.observe(viewLifecycleOwner) { timeSing.text = it }
            viewModel.isFavoriteState.observe(viewLifecycleOwner) { isFavorite ->
                likeButton.setImageResource(
                    if (isFavorite == true) {
                        R.drawable.favorite
                    } else {
                        R.drawable.like
                    }
                )
            }
            viewModel.playerState.observe(viewLifecycleOwner) { state ->
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
            viewModel.playlistsState.observe(viewLifecycleOwner) { playlists ->
                setDataToAdapter(playlists)
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

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter(playlists: List<Playlist>) {
        adapter.playlists = playlists
        adapter.notifyDataSetChanged()
    }

    private fun playlistClickDebounce(): Boolean {
        val current = isPlaylistClickAllowed
        if (isPlaylistClickAllowed) {
            isPlaylistClickAllowed = false
            lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isPlaylistClickAllowed = true
            }
        }
        return current
    }

    companion object {
        const val TRACK_KEY = "track_key"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
