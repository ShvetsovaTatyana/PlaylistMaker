package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentPlaylistsBinding
import com.github.ilyashvetsov.playlistmaker.library.playlists.domain.model.Playlist
import com.github.ilyashvetsov.playlistmaker.library.playlists.ui.playlist.PlaylistFragment
import com.github.ilyashvetsov.playlistmaker.library.root.ui.BaseSectionFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : BaseSectionFragment() {
    private lateinit var binding: FragmentPlaylistsBinding
    private val viewModel by viewModel<PlaylistsViewModel>()

    override val titleRes: Int = R.string.library_playlists_title

    private var isPlaylistClickAllowed = true
    private val adapter: AdapterPlaylist by lazy {
        AdapterPlaylist { playlist ->
            if (playlistClickDebounce()) {
                openPlaylistPage(playlist)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createPlaylistButton.setOnClickListener {
                findNavController().navigate(R.id.action_libraryFragment_to_createPlaylistFragment)
            }

            recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

            viewModel.screenState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    PlaylistsScreenState.Loading -> {
                        recyclerView.isVisible = false
                        placeHolderEmptyImage.isVisible = false
                        placeHolderEmptyText.isVisible = false
                    }

                    PlaylistsScreenState.Empty -> {
                        recyclerView.isVisible = false
                        placeHolderEmptyImage.isVisible = true
                        placeHolderEmptyText.isVisible = true
                    }

                    is PlaylistsScreenState.Data -> {
                        recyclerView.isVisible = true
                        placeHolderEmptyImage.isVisible = false
                        placeHolderEmptyText.isVisible = false
                        setDataToAdapter(state.playlists)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateData()
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter(playlists: List<Playlist>) {
        adapter.playlists = playlists
        adapter.notifyDataSetChanged()
    }

    private fun openPlaylistPage(playlist: Playlist) {
        findNavController().navigate(
            resId = R.id.action_navigation_library_to_navigation_playlist,
            args = bundleOf(PlaylistFragment.PLAYLIST_KEY to playlist)
        )
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L

        @JvmStatic
        fun newInstance(): PlaylistsFragment {
            return PlaylistsFragment()
        }
    }
}
