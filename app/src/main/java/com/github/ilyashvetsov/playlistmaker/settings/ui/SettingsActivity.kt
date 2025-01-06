package com.github.ilyashvetsov.playlistmaker.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.ilyashvetsov.playlistmaker.creator.Creator
import com.github.ilyashvetsov.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            owner = this,
            factory = SettingsViewModel.getViewModelFactory(
                Creator.getSharingInteractor(this),
                Creator.getThemeInteractor(this)
            )
        )[SettingsViewModel::class.java]

        with(binding) {
            buttonBack.setOnClickListener { finish() }
            shareButton.setOnClickListener { viewModel.shareApp() }
            supportButton.setOnClickListener { viewModel.openSupport() }
            termsOfUseButton.setOnClickListener { viewModel.openTerms() }
            themeSwitcher.setOnCheckedChangeListener { _, checked ->
                viewModel.setDarkTheme(checked)
            }
            themeSwitcher.isChecked = viewModel.isDarkTheme()
        }
    }
}
