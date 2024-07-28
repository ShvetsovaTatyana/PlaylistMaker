package com.github.ilyashvetsov.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    var text: String = ""
    val adapter: AdapterTrack = AdapterTrack()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesApiService = retrofit.create<ItunesApiService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        placeHolderError = findViewById(R.id.place_holder_error)
        placeHolderErrorText = findViewById(R.id.place_holder_error_text)
        placeHolderErrorButton = findViewById(R.id.place_holder_error_button)
        placeHolderEmptyImage = findViewById(R.id.place_holder_empty_image)
        placeHolderEmptyText = findViewById(R.id.place_holder_empty_text)
        textSearch = findViewById<EditText>(R.id.text_search)
        val clearButton = findViewById<ImageButton>(R.id.clear_button)
        val buttonBackArrow = findViewById<ImageButton>(R.id.button_back_arrow)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        buttonBackArrow.setOnClickListener {
            finish()
        }
        clearButton.setOnClickListener {
            textSearch.setText("")
            textSearch.clearFocus()
            adapter.trackList = arrayListOf()
            adapter.notifyDataSetChanged()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textSearch.windowToken, 0)
        }
        placeHolderErrorButton.setOnClickListener {
            search()
        }

        textSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty())
                    clearButton.visibility = View.INVISIBLE
                else
                    clearButton.visibility = View.VISIBLE
                text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) = Unit

        }
        textSearch.addTextChangedListener(simpleTextWatcher)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("string", text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text = savedInstanceState.getString("string").toString()
        val textSearch = findViewById<EditText>(R.id.text_search)
        textSearch.setText(text)
    }

    fun showError() {
        recyclerView.visibility = View.GONE
        placeHolderError.visibility = View.VISIBLE
        placeHolderErrorText.visibility = View.VISIBLE
        placeHolderErrorButton.visibility = View.VISIBLE
        placeHolderEmptyImage.visibility = View.GONE
        placeHolderEmptyText.visibility = View.GONE
    }

    fun search() {
        itunesApiService.getTrackList(textSearch.text.toString())
            .enqueue(object : Callback<ListTrackResponse> {
                override fun onResponse(
                    call: Call<ListTrackResponse>,
                    response: Response<ListTrackResponse>
                ) {
                    if (response.code() == 200) {
                        val trackList = response.body()
                        if (trackList != null && trackList.results.size > 0) {
                            recyclerView.visibility = View.VISIBLE
                            placeHolderError.visibility = View.GONE
                            placeHolderErrorText.visibility = View.GONE
                            placeHolderErrorButton.visibility = View.GONE
                            placeHolderEmptyImage.visibility = View.GONE
                            placeHolderEmptyText.visibility = View.GONE
                            adapter.trackList = trackList.results
                            adapter.notifyDataSetChanged()
                        } else {
                            placeHolderEmptyImage.visibility = View.VISIBLE
                            placeHolderEmptyText.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                            placeHolderError.visibility = View.GONE
                            placeHolderErrorText.visibility = View.GONE
                            placeHolderErrorButton.visibility = View.GONE
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