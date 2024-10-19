package com.decide.app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateFormatter(date: Long): String {
    val dates = Date(date)
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    return sdf.format(dates)
}