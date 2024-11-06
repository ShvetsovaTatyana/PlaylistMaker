package com.github.ilyashvetsov.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class SearchActivity : AppCompatActivity() {
    lateinit var placeHolderEmptyText: TextView
    lateinit var placeHolderEmptyImage: ImageView
    lateinit var placeHolderError: ImageView
    lateinit var placeHolderErrorText: TextView
    lateinit var placeHolderErrorButton: Button
    lateinit var recyclerView: RecyclerView
    lateinit var textSearch: EditText
    lateinit var clearHistoryButton: Button
    lateinit var titleText: TextView
    lateinit var progressCircular: ProgressBar

    private var text: String = ""
    private val gson = Gson()
    private lateinit var adapter: AdapterTrack
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesApiService = retrofit.create<ItunesApiService>()

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchRequest() }
    private var isClickAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        titleText = findViewById(R.id.title_text)
        clearHistoryButton = findViewById(R.id.clear_history_button)
        placeHolderError = findViewById(R.id.place_holder_error)
        placeHolderErrorText = findViewById(R.id.place_holder_error_text)
        placeHolderErrorButton = findViewById(R.id.place_holder_error_button)
        placeHolderEmptyImage = findViewById(R.id.place_holder_empty_image)
        placeHolderEmptyText = findViewById(R.id.place_holder_empty_text)
        progressCircular = findViewById(R.id.progress_circular)
        textSearch = findViewById(R.id.text_search)
        recyclerView = findViewById(R.id.recyclerview)

        val sharedPrefs = getSharedPreferences(HISTORY_SHARED_PREFERENCES, MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPrefs)
        val clearButton = findViewById<ImageButton>(R.id.clear_button)
        val buttonBackArrow = findViewById<ImageButton>(R.id.button_back_arrow)

        placeHolderErrorButton.setOnClickListener { searchRequest() }
        buttonBackArrow.setOnClickListener { finish() }
        clearButton.setOnClickListener {
            textSearch.setText("")
            textSearch.clearFocus()
            adapter.trackList = arrayListOf()
            adapter.notifyDataSetChanged()

            placeHolderError.visibility = View.GONE
            placeHolderErrorText.visibility = View.GONE
            placeHolderErrorButton.visibility = View.GONE
            placeHolderEmptyImage.visibility = View.GONE
            placeHolderEmptyText.visibility = View.GONE

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textSearch.windowToken, 0)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text = s.toString()
                searchDebounce()

                clearButton.visibility = if (s.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE

                if (textSearch.hasFocus()
                    && s?.isEmpty() == true
                    && searchHistory.getTrackList().isNotEmpty()
                ) {
                    clearHistoryButton.visibility = View.VISIBLE
                    titleText.visibility = View.VISIBLE
                } else {
                    clearHistoryButton.visibility = View.GONE
                    titleText.visibility = View.GONE
                }

                if (s?.isEmpty() == false) {
                    adapter.trackList.clear()
                } else {
                    adapter.trackList = searchHistory.getTrackList()
                }

                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }

        textSearch.addTextChangedListener(simpleTextWatcher)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = AdapterTrack(
            onItemClickListener = { track ->
                if (clickDebounce()) {
                    val intent = Intent(this, AudioPlayerActivity::class.java)
                    intent.putExtra(AudioPlayerActivity.TRACK_KEY, gson.toJson(track))
                    startActivity(intent)
                    searchHistory.saveTrack(track)
                }
            }
        )
        recyclerView.adapter = adapter

        clearHistoryButton.setOnClickListener {
            searchHistory.deleteTrackList()
            adapter.trackList.clear()
            adapter.notifyDataSetChanged()
            if (searchHistory.getTrackList().isNotEmpty()) {
                clearHistoryButton.visibility = View.VISIBLE
                titleText.visibility = View.VISIBLE
            } else {
                clearHistoryButton.visibility = View.GONE
                titleText.visibility = View.GONE
            }
        }

        textSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && textSearch.text.isEmpty() && searchHistory.getTrackList().isNotEmpty()) {
                clearHistoryButton.visibility = View.VISIBLE
                titleText.visibility = View.VISIBLE
            } else {
                clearHistoryButton.visibility = View.GONE
                titleText.visibility = View.GONE
            }
            if (hasFocus && textSearch.text.isEmpty()) {
                adapter.trackList = searchHistory.getTrackList()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("string", text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text = savedInstanceState.getString("string").toString()
        textSearch.setText(text)
    }

    fun showError() {
        recyclerView.visibility = View.GONE
        placeHolderError.visibility = View.VISIBLE
        placeHolderErrorText.visibility = View.VISIBLE
        placeHolderErrorButton.visibility = View.VISIBLE
        placeHolderEmptyImage.visibility = View.GONE
        placeHolderEmptyText.visibility = View.GONE
        progressCircular.visibility = View.GONE
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun searchRequest() {
        progressCircular.visibility = View.VISIBLE
        placeHolderError.visibility = View.GONE
        placeHolderErrorText.visibility = View.GONE
        placeHolderErrorButton.visibility = View.GONE
        placeHolderEmptyImage.visibility = View.GONE
        placeHolderEmptyText.visibility = View.GONE

        if (textSearch.text.toString().isEmpty()) {
            progressCircular.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            itunesApiService.getTrackList(textSearch.text.toString())
                .enqueue(object : Callback<ListTrackResponse> {
                    override fun onResponse(
                        call: Call<ListTrackResponse>,
                        response: Response<ListTrackResponse>
                    ) {
                        if (response.code() == 200) {
                            val trackList = response.body()
                            if (trackList != null && trackList.results.isNotEmpty()) {
                                recyclerView.visibility = View.VISIBLE
                                placeHolderError.visibility = View.GONE
                                placeHolderErrorText.visibility = View.GONE
                                placeHolderErrorButton.visibility = View.GONE
                                placeHolderEmptyImage.visibility = View.GONE
                                placeHolderEmptyText.visibility = View.GONE
                                progressCircular.visibility = View.GONE
                                adapter.trackList = trackList.results as ArrayList<Track>
                                adapter.notifyDataSetChanged()
                            } else {
                                placeHolderEmptyImage.visibility = View.VISIBLE
                                placeHolderEmptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                                placeHolderError.visibility = View.GONE
                                placeHolderErrorText.visibility = View.GONE
                                placeHolderErrorButton.visibility = View.GONE
                                progressCircular.visibility = View.GONE
                            }
                        } else {
                            showError()
                        }
                    }

                    override fun onFailure(call: Call<ListTrackResponse>, t: Throwable) {
                        showError()
                    }
                })
        }
    }

    companion object {
        private const val HISTORY_SHARED_PREFERENCES = "history_shared_preferences"
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
