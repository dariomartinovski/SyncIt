package mk.ukim.finki.syncit.utils

import android.annotation.SuppressLint
import java.util.Date
import java.util.Locale

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun Long.toFormattedDate(pattern: String = "yyyy-MM-dd"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(Date(this))
}

fun LocalDate.toFormattedDate(): String =
    this.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

fun LocalTime.toFormattedTime(): String =
    this.format(DateTimeFormatter.ofPattern("HH:mm"))

fun LocalDateTime.toFormattedDate(): String =
    this.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

@SuppressLint("DefaultLocale")
fun Pair<Int, Int>.toFormattedTime(): String {
    val (hour, minute) = this
    return String.format("%02d:%02d", hour, minute)
}

fun LocalDateTime.toSimpleFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return this.format(formatter)
}