package com.github.ilyashvetsov.playlistmaker.sharing.domain

import com.github.ilyashvetsov.playlistmaker.R
import com.github.ilyashvetsov.playlistmaker.sharing.domain.model.EmailData

interface SharingInteractor {
    fun shareApp()
    fun openTerms()
    fun openSupport()
}

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return "https://practicum.yandex.ru/profile/android-developer/"
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(
            emails = arrayOf("tatyanapolinko@yandex.ru"),
            subject = R.string.support_email_subject,
            text = R.string.support_email_text
        )
    }

    private fun getTermsLink(): String {
        return "https://yandex.ru/legal/practicum_offer/"
    }
}
