package io.lb.presentation.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun Long.toDateFormat(): String {
    val date = Date(this)
    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return formatter.format(date)
}
