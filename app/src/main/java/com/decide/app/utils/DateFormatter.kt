package com.decide.app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateFormatter(millis: Long?): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return if (millis != null) {
        formatter.format(Date(millis))
    } else {
        ""
    }
}