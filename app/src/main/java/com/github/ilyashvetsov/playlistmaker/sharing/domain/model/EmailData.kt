package com.github.ilyashvetsov.playlistmaker.sharing.domain.model

import androidx.annotation.StringRes

class EmailData(
    val emails: Array<String>,
    @StringRes val subject: Int,
    @StringRes val text: Int
)
