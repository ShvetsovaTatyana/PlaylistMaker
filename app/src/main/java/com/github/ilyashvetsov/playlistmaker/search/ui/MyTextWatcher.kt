package com.github.ilyashvetsov.playlistmaker.search.ui

import android.text.Editable
import android.text.TextWatcher

fun interface MyTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}
