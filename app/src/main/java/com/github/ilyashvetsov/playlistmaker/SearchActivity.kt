package com.github.ilyashvetsov.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val textSearch = findViewById<EditText>(R.id.text_search)
        val clearButton = findViewById<ImageButton>(R.id.clear_button)
        clearButton.setOnClickListener {
            textSearch.setText("")
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty())
                    clearButton.visibility = View.INVISIBLE
                else
                    clearButton.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }

        }
        textSearch.addTextChangedListener(simpleTextWatcher)
    }
}