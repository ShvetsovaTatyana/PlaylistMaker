package com.github.ilyashvetsov.playlistmaker.search.ui

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentSearchBinding
import com.github.ilyashvetsov.playlistmaker.player.ui.AudioPlayerActivity
import com.github.ilyashvetsov.playlistmaker.search.domain.model.Track
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<SearchViewModel>()

    private val textWatcher = MyTextWatcher { text, _, _, _ ->
        searchDebounce()
        binding.clearButton.isVisible = text.isNotEmpty()
    }

    private val adapter: AdapterTrack by lazy {
        AdapterTrack { track ->
            if (trackClickDebounce()) {
                viewModel.saveTrack(track)
                openAudioPlayer(track)
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchRequest() }
    private var isTrackClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

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
        }
        handleScreenState()
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            clearButton.isVisible = textSearch.text.isNotEmpty()

            textSearch.addTextChangedListener(textWatcher)

            textSearch.setOnFocusChangeListener { _, hasFocus ->
                viewModel.changeFocus(
                    textSearch = textSearch.text.toString(),
                    hasFocus = hasFocus
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.textSearch.removeTextChangedListener(textWatcher)
        binding.textSearch.onFocusChangeListener = null
    }

    private fun handleScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchScreenState.Init -> {
                    clearAdapterData()
                    handleViewsVisibility(ViewType.RECYCLER_VIEW)
                }

                SearchScreenState.Loading -> {
                    handleViewsVisibility(ViewType.LOADER)
                }

                is SearchScreenState.SearchData -> {
                    handleViewsVisibility(ViewType.RECYCLER_VIEW)
                    setDataToAdapter(state.trackList)
                }

                is SearchScreenState.HistoryData -> {
                    handleViewsVisibility(setOf(ViewType.RECYCLER_VIEW, ViewType.HISTORY))
                    setDataToAdapter(state.trackList)
                }

                SearchScreenState.Empty -> {
                    handleViewsVisibility(ViewType.EMPTY)
                }

                is SearchScreenState.Error -> {
                    handleViewsVisibility(ViewType.ERROR)
                }
            }
        }
    }

    private fun handleViewsVisibility(visibleViewType: ViewType) {
        handleViewsVisibility(setOf(visibleViewType))
    }

    private fun handleViewsVisibility(visibleViewTypes: Set<ViewType>) = with(binding) {
        recyclerView.isVisible = ViewType.RECYCLER_VIEW in visibleViewTypes

        clearHistoryButton.isVisible = ViewType.HISTORY in visibleViewTypes
        historyTitleText.isVisible = ViewType.HISTORY in visibleViewTypes

        progressCircular.isVisible = ViewType.LOADER in visibleViewTypes

        placeHolderEmptyImage.isVisible = ViewType.EMPTY in visibleViewTypes
        placeHolderEmptyText.isVisible = ViewType.EMPTY in visibleViewTypes

        placeHolderError.isVisible = ViewType.ERROR in visibleViewTypes
        placeHolderErrorText.isVisible = ViewType.ERROR in visibleViewTypes
        placeHolderErrorButton.isVisible = ViewType.ERROR in visibleViewTypes
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter(trackList: List<Track>) {
        adapter.trackList = trackList
        adapter.notifyDataSetChanged()
    }

    private fun clearAdapterData() {
        setDataToAdapter(trackList = emptyList())
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
        val intent = Intent(context, AudioPlayerActivity::class.java)
        intent.putExtra(AudioPlayerActivity.TRACK_KEY, track)
        startActivity(intent)
    }

    private fun hideKeyboard() = with(binding) {
        textSearch.clearFocus()
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(textSearch.windowToken, 0)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private enum class ViewType {
        RECYCLER_VIEW,
        HISTORY,
        LOADER,
        EMPTY,
        ERROR
    }
}
