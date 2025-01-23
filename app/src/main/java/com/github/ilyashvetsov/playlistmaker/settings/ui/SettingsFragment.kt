package com.github.ilyashvetsov.playlistmaker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
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
