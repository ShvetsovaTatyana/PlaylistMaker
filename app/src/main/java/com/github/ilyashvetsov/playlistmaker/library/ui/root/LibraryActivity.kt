package com.github.ilyashvetsov.playlistmaker.library.ui.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.databinding.ActivityLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLibraryBinding
    private lateinit var tabMediator: TabLayoutMediator
    private lateinit var adapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackArrow.setOnClickListener { finish() }

        adapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = adapter

        tabMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(adapter.getTabTitleRes(position))
        }
        tabMediator.attach()

        val tabColor = getColor(R.color.arrow)
        binding.tabs.setTabTextColors(tabColor, tabColor)
        binding.tabs.setSelectedTabIndicatorColor(tabColor)
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}
