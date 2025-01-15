package com.github.ilyashvetsov.playlistmaker.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ilyashvetsov.playlistmaker.databinding.ActivitySettingsBinding
import com.github.ilyashvetsov.playlistmaker.settings.domain.ThemeInteractor
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
