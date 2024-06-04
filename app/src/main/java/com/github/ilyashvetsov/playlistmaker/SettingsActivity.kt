package com.github.ilyashvetsov.playlistmaker

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val button_back = findViewById<Button>(R.id.button_back)
        button_back.setOnClickListener() {
            finish()
        }
    }
}