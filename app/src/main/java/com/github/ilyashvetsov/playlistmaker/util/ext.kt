package com.github.ilyashvetsov.playlistmaker.util

import android.content.res.Resources.getSystem

// TODO use TypedValue.applyDimension
val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
