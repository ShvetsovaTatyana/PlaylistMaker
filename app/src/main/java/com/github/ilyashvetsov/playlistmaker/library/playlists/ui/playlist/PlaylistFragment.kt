package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.playlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentPlaylistBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerFragment
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.track.presentation.AdapterTrack
import com.github.ilyashvetsov.playlistmaker.util.px
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private val viewModel by viewModel<PlaylistViewModel>()

    private lateinit var menuBehavior: BottomSheetBehavior<LinearLayout>

    private var isTrackClickAllowed = true
    private val adapter: AdapterTrack by lazy {
        AdapterTrack(
            onItemClickListener = { track ->
                if (trackClickDebounce()) {
                    openAudioPlayer(track)
                }
            },
            onItemLongClickListener = ::removeTrack
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initPlaylist = requireArguments().getParcelable<Playlist>(PLAYLIST_KEY)
            ?: throw Exception("playlist is null")

        viewModel.update(initPlaylist)

        with(binding) {
            buttonBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }

            ivShare.setOnClickListener { share() }
            ivMore.setOnClickListener {
                menuBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            shareButton.setOnClickListener { share() }
            editButton.setOnClickListener { edit() }
            removeButton.setOnClickListener { removePlaylist() }

            menuBehavior = BottomSheetBehavior.from(menu).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
            }

            menuBehavior.addBottomSheetCallback(
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

            viewModel.playlistState.observe(viewLifecycleOwner) {
                it?.let { playlist ->
                    nameTextView.text = playlist.name
                    descriptionTextView.text = playlist.description
                    trackCount.text = "Треков: ${playlist.trackIds.size}"

                    Glide.with(this@PlaylistFragment)
                        .load(playlist.imagePath?.let { path -> File(path) })
                        .placeholder(R.drawable.placeholder)
                        .transform(CenterCrop(), RoundedCorners(8.px))
                        .into(ivImageAlbum)
                }
            }

            viewModel.allTimeState.observe(viewLifecycleOwner) {
                allTime.text = "$it минут"
            }

            viewModel.screenState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    PlaylistScreenState.Loading -> {
                        recyclerView.isVisible = false
                        placeHolderEmptyImage.isVisible = false
                        placeHolderEmptyText.isVisible = false
                    }

                    PlaylistScreenState.Empty -> {
                        recyclerView.isVisible = false
                        placeHolderEmptyImage.isVisible = true
                        placeHolderEmptyText.isVisible = true
                    }

                    is PlaylistScreenState.Data -> {
                        recyclerView.isVisible = true
                        placeHolderEmptyImage.isVisible = false
                        placeHolderEmptyText.isVisible = false
                        setDataToAdapter(state.trackList)
                    }
                }
            }
        }
    }

    private fun trackClickDebounce(): Boolean {
        val current = isTrackClickAllowed
        if (isTrackClickAllowed) {
            isTrackClickAllowed = false
            lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isTrackClickAllowed = true
            }
        }
        return current
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter(trackList: List<Track>) {
        adapter.trackList = trackList
        adapter.notifyDataSetChanged()
    }

    private fun openAudioPlayer(track: Track) {
        findNavController().navigate(
            resId = R.id.action_navigation_playlist_to_navigation_audio_player,
            args = bundleOf(AudioPlayerFragment.TRACK_KEY to track)
        )
    }

    private fun removeTrack(track: Track) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Хотите удалить трек?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.removeTrack(track)
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }

    private fun share() {
        viewModel.playlistState.value?.let { playlist ->
            if (playlist.trackIds.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "В этом плейлисте нет списка треков, которым можно поделиться",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.share()
            }
        }
    }

    private fun edit() {

    }

    private fun removePlaylist() {
        viewModel.playlistState.value?.let { playlist ->
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("Хотите удалить плейлист ${playlist.name}?")
                .setPositiveButton("Да") { _, _ ->
                    viewModel.removePlaylist()
                    findNavController().navigateUp()
                }
                .setNegativeButton("Нет") { _, _ -> }
                .show()
        }
    }

    companion object {
        const val PLAYLIST_KEY = "playlist_key"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
