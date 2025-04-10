package com.github.ilyashvetsov.playlistmaker.library.favorite.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.github.ilyashvetsov.playlistmaker.library.root.ui.BaseSectionFragment
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerFragment
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import com.github.ilyashvetsov.playlistmaker.track.presentation.AdapterTrack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment : BaseSectionFragment() {
    private lateinit var binding: FragmentFavoriteTracksBinding
    private val viewModel by viewModel<FavoriteTracksViewModel>()

    override val titleRes: Int = R.string.library_favorite_tracks_title

    private var isTrackClickAllowed = true
    private val adapter: AdapterTrack by lazy {
        AdapterTrack { track ->
            if (trackClickDebounce()) {
                openAudioPlayer(track)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

            viewModel.screenState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    FavoriteScreenState.Loading -> {
                        recyclerView.isVisible = false
                        placeHolderEmptyImage.isVisible = false
                        placeHolderEmptyText.isVisible = false
                    }

                    FavoriteScreenState.Empty -> {
                        recyclerView.isVisible = false
                        placeHolderEmptyImage.isVisible = true
                        placeHolderEmptyText.isVisible = true
                    }

                    is FavoriteScreenState.Data -> {
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
            resId = R.id.action_navigation_library_to_navigation_audio_player,
            args = bundleOf(AudioPlayerFragment.TRACK_KEY to track)
        )
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L

        @JvmStatic
        fun newInstance(): FavoriteTracksFragment {
            return FavoriteTracksFragment()
        }
    }
}
