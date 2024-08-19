package com.decide.app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateFormatter(date: String): String {
    val millis = date.toLong()

    val dates = Date(millis)
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    return sdf.format(dates)
}