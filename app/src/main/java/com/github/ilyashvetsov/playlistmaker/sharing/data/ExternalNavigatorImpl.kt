package com.github.ilyashvetsov.playlistmaker.sharing.data

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.github.ilyashvetsov.playlistmaker.sharing.domain.ExternalNavigator
import com.github.ilyashvetsov.playlistmaker.sharing.domain.model.EmailData

class ExternalNavigatorImpl(
    private val context: Context
) : ExternalNavigator {
    override fun share(text: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooserIntent = Intent.createChooser(intent, "")
        chooserIntent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

    override fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openEmail(emailData: EmailData) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, emailData.emails)
            putExtra(Intent.EXTRA_SUBJECT, context.getString(emailData.subject))
            putExtra(Intent.EXTRA_TEXT, context.getString(emailData.text))
            setFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
