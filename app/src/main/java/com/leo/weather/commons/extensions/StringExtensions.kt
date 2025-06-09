package com.leo.weather.commons.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.getDayAndMonthFromDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    val date: Date? = formatter.parse(this)
    return if (date != null) {
        val day = SimpleDateFormat("d", Locale.getDefault()).format(date)
        val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(date)
        "$day, $monthName"
    } else {
        ""
    }
}

fun String.getWeekdayFromDate(): String {
    val formats = listOf(
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()),
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    )
    formats.forEach { it.timeZone = TimeZone.getDefault() }
    val date: Date? = formats.firstNotNullOfOrNull { runCatching { it.parse(this) }.getOrNull() }
    if (date != null) {
        val calendar = java.util.Calendar.getInstance()
        val today = calendar.clone() as java.util.Calendar
        val tomorrow = calendar.clone() as java.util.Calendar
        tomorrow.add(java.util.Calendar.DAY_OF_YEAR, 1)

        val dateCal = java.util.Calendar.getInstance().apply { time = date }

        fun isSameDay(cal1: java.util.Calendar, cal2: java.util.Calendar): Boolean {
            return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
                    cal1.get(java.util.Calendar.DAY_OF_YEAR) == cal2.get(java.util.Calendar.DAY_OF_YEAR)
        }

        return when {
            isSameDay(dateCal, today) -> "today"
            isSameDay(dateCal, tomorrow) -> "tomorrow"
            else -> SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
        }
    }
    return ""
}

fun String.getHourFromDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    val date: Date? = formatter.parse(this)
    return if (date != null) {
        val hour = SimpleDateFormat("HH", Locale.getDefault()).format(date)
        hour
    } else {
        ""
    }
}