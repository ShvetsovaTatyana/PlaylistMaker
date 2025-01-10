package com.github.ilyashvetsov.playlistmaker.sharing.domain

import com.github.ilyashvetsov.playlistmaker.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun openLink(link: String)
    fun openEmail(emailData: EmailData)
}
