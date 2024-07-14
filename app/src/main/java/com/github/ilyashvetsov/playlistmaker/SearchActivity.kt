package com.github.ilyashvetsov.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    var text: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val textSearch = findViewById<EditText>(R.id.text_search)
        val clearButton = findViewById<ImageButton>(R.id.clear_button)
        val buttonBackArrow = findViewById<ImageButton>(R.id.button_back_arrow)
        buttonBackArrow.setOnClickListener {
            finish()

        }
        clearButton.setOnClickListener {
            textSearch.setText("")
            textSearch.clearFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textSearch.windowToken, 0)
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
}