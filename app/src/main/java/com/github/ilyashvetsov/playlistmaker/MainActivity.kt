package com.github.ilyashvetsov.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var search = findViewById<Button>(R.id.search)
        search.setOnClickListener(searchClickListener)
        var library = findViewById<Button>(R.id.library)
        library.setOnClickListener {
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
        }
        var settings = findViewById<Button>(R.id.settings)
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    val searchClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

}