package com.github.ilyashvetsov.playlistmaker.library.root.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var tabMediator: TabLayoutMediator
    private lateinit var adapter: SectionsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SectionsPagerAdapter(this.requireActivity())
        binding.viewPager.adapter = adapter

        tabMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(adapter.getTabTitleRes(position))
        }
        tabMediator.attach()

        val tabColor = requireContext().getColor(R.color.arrow)
        binding.tabs.setTabTextColors(tabColor, tabColor)
        binding.tabs.setSelectedTabIndicatorColor(tabColor)
    }

    override fun onDestroyView() {
        tabMediator.detach()
        super.onDestroyView()
    }
}
