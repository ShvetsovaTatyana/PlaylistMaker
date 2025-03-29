package com.github.ilyashvetsov.playlistmaker.library.playlists.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentPlaylistsBinding
import com.github.ilyashvetsov.playlistmaker.library.root.ui.BaseSectionFragment

class PlaylistsFragment : BaseSectionFragment() {
    private lateinit var viewModel: PlaylistsViewModel
    private lateinit var binding: FragmentPlaylistsBinding

    override val titleRes: Int = R.string.library_playlists_title

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): PlaylistsFragment {
            return PlaylistsFragment()
        }
    }
}
