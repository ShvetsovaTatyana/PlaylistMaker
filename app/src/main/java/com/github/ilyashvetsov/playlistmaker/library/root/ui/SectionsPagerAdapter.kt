package com.github.ilyashvetsov.playlistmaker.library.root.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.ilyashvetsov.playlistmaker.library.favorite.ui.FavoriteTracksFragment
import com.github.ilyashvetsov.playlistmaker.library.playlists.ui.allplaylists.PlaylistsFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = arrayOf(
        FavoriteTracksFragment.newInstance(),
        PlaylistsFragment.newInstance()
    )

    fun getTabTitleRes(position: Int) = fragments[position].titleRes

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
