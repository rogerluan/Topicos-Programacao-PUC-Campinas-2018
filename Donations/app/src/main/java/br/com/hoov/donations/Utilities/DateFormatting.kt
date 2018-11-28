package br.com.hoov.donations.Utilities

import android.content.Context
import android.text.format.DateUtils
import br.com.hoov.donations.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

val defaultLocale: Locale get() = Locale.getDefault()

private val timeFormatter = SimpleDateFormat( "h:mm a", defaultLocale)                                              // e.g. 7:04 PM
private val longDayOfWeekAndTimeFormatter = SimpleDateFormat( "EEEE h:mm a", defaultLocale)                         // e.g. Tuesday 7:04 PM
private val mediumDayOfWeekAndDateAndTimeFormatter = SimpleDateFormat( "EEE, MMM d, yyyy, h:mm a", defaultLocale)   // e.g. Tue, Jan 1, 2017, 7:04 PM

fun Date.daysSince(other: Date): Int {
    val milliseconds = this.time - other.time
    val days = milliseconds.toDouble() / (1000 * 60 * 60 * 24).toDouble()
    return days.roundToInt()
}

fun Date.autoFormatted(context: Context): String {
    val now = Date()
    // Today
    if (DateUtils.isToday(this.time)) return timeFormatter.format(this)
    // Yesterday or tomorrow
    if (now.daysSince(this) == 1) return String.format(context.getString(R.string.date_formatting_yesterday_time), timeFormatter.format(this))
    if (now.daysSince(this) == -1) return String.format(context.getString(R.string.date_formatting_tomorrow_time), timeFormatter.format(this))
    // Last/Next week
    if (abs(now.daysSince(this)) < 7) {
        return longDayOfWeekAndTimeFormatter.format(this)
    }
    // Other
    return mediumDayOfWeekAndDateAndTimeFormatter.format(this)
}
