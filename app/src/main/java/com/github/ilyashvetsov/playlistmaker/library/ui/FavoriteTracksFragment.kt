package com.github.ilyashvetsov.playlistmaker.library.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.github.ilyashvetsov.playlistmaker.library.ui.root.BaseSectionFragment

class FavoriteTracksFragment : BaseSectionFragment() {
    private lateinit var viewModel: FavoriteTracksViewModel
    private lateinit var binding: FragmentFavoriteTracksBinding

    override val titleRes: Int = R.string.library_favorite_tracks_title

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteTracksFragment {
            return FavoriteTracksFragment()
        }
    }
}
