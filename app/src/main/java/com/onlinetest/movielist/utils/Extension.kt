package com.onlinetest.movielist.utils

import java.text.SimpleDateFormat
import java.util.Date

fun String.toMilliSeconds(): Long {
    val sdf = SimpleDateFormat("d MMM yyyy")
    val date: Date = sdf.parse(this)
    return date.time
}

fun Long.toDateFormat(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format)
    val dateString = simpleDateFormat.format(this)
    return String.format(dateString)
}