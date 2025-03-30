package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentPlaylistsBinding
import com.github.ilyashvetsov.playlistmaker.library.root.ui.BaseSectionFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : BaseSectionFragment() {
    private lateinit var binding: FragmentPlaylistsBinding
    private val viewModel by viewModel<PlaylistsViewModel>()

    override val titleRes: Int = R.string.library_playlists_title

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_createPlaylistFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): PlaylistsFragment {
            return PlaylistsFragment()
        }
    }
}
