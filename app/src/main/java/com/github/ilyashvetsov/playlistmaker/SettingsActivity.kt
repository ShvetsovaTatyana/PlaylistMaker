package com.github.ilyashvetsov.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonBack = findViewById<Button>(R.id.button_back)
        buttonBack.setOnClickListener {
            finish()
        }
        val shareButton = findViewById<TextView>(R.id.share_button)
        shareButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                this.getString(R.string.share_button)
            )
            intent.setType("text/plain")
            startActivity(Intent.createChooser(intent, " "))
        }
        val supportButton = findViewById<TextView>(R.id.support_button)
        supportButton.setOnClickListener {
            val message = this.getString(R.string.support_button_message)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("tatyanapolinko@yandex.ru"))
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT, this.getString(R.string.message)
            )
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
        val termsOfUseButton = findViewById<TextView>(R.id.terms_of_use_button)
        termsOfUseButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(this.getString(R.string.terms_of_use_button))
                )
            )
        }
    }
}