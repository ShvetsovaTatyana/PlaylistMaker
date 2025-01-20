package com.github.ilyashvetsov.playlistmaker.library.ui.root

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.ilyashvetsov.playlistmaker.library.ui.FavoriteTracksFragment
import com.github.ilyashvetsov.playlistmaker.library.ui.PlaylistsFragment

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
