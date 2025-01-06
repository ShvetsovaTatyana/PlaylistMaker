package com.github.ilyashvetsov.playlistmaker.search.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.creator.Creator
import com.github.ilyashvetsov.playlistmaker.databinding.ActivitySearchBinding
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerActivity
import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private val adapter: AdapterTrack by lazy {
        AdapterTrack { track ->
            if (trackClickDebounce()) {
                viewModel.saveTrack(track)
                openAudioPlayer(track)
            }
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(
            owner = this,
            factory = SearchViewModel.getViewModelFactory(
                searchTracksUseCase = Creator.getSearchTracksUseCase(),
                searchHistoryInteractor = Creator.getSearchHistoryInteractor(this)
            )
        )[SearchViewModel::class.java]
    }

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchRequest() }
    private var isTrackClickAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

            buttonBackArrow.setOnClickListener { finish() }
            clearButton.setOnClickListener {
                textSearch.setText("")
                viewModel.clearButtonClick()
                hideKeyboard()
            }
            clearHistoryButton.setOnClickListener {
                viewModel.clearHistory()
                clearAdapterData()
            }

            placeHolderErrorButton.setOnClickListener { searchRequest() }

            textSearch.addTextChangedListener(
                MyTextWatcher { text, _, _, _ ->
                    searchDebounce()
                    clearButton.isVisible = text.isNotEmpty()
                }
            )

            textSearch.setOnFocusChangeListener { _, hasFocus ->
                viewModel.changeFocus(
                    textSearch = textSearch.text.toString(),
                    hasFocus = hasFocus
                )
            }
        }
        handleScreenState()
    }

    private fun handleScreenState() {
        viewModel.screenState.observe(this@SearchActivity) { state ->
            when (state) {
                SearchScreenState.Init -> {
                    clearAdapterData()
                    handleViewsVisibility(setOf(0))
                }

                SearchScreenState.Loading -> {
                    handleViewsVisibility(setOf(2))
                }

                is SearchScreenState.SearchData -> {
                    handleViewsVisibility(setOf(0))
                    setDataToAdapter(state.trackList)
                }

                is SearchScreenState.HistoryData -> {
                    handleViewsVisibility(setOf(0, 1))
                    setDataToAdapter(state.trackList)
                }

                SearchScreenState.Empty -> {
                    handleViewsVisibility(setOf(3))
                }

                is SearchScreenState.Error -> {
                    handleViewsVisibility(setOf(4))
                }
            }
        }
    }

    private fun handleViewsVisibility(visibleIndexes: Set<Int>) = with(binding) {
        recyclerView.isVisible = 0 in visibleIndexes

        clearHistoryButton.isVisible = 1 in visibleIndexes
        historyTitleText.isVisible = 1 in visibleIndexes

        progressCircular.isVisible = 2 in visibleIndexes

        placeHolderEmptyImage.isVisible = 3 in visibleIndexes
        placeHolderEmptyText.isVisible = 3 in visibleIndexes

        placeHolderError.isVisible = 4 in visibleIndexes
        placeHolderErrorText.isVisible = 4 in visibleIndexes
        placeHolderErrorButton.isVisible = 4 in visibleIndexes
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter(trackList: ArrayList<Track>) {
        adapter.trackList = trackList
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearAdapterData() {
        adapter.trackList.clear()
        adapter.notifyDataSetChanged()
    }

    private fun searchRequest() {
        viewModel.searchTracks(
            expression = binding.textSearch.text.toString()
        )
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun trackClickDebounce(): Boolean {
        val current = isTrackClickAllowed
        if (isTrackClickAllowed) {
            isTrackClickAllowed = false
            handler.postDelayed({ isTrackClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun openAudioPlayer(track: Track) {
        val intent = Intent(this, AudioPlayerActivity::class.java)
        intent.putExtra(AudioPlayerActivity.TRACK_KEY, Gson().toJson(track))
        startActivity(intent)
    }

    private fun hideKeyboard() = with(binding) {
        textSearch.clearFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(textSearch.windowToken, 0)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
