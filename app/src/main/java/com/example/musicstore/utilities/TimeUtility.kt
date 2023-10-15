package com.example.musicstore.utilities

import java.util.concurrent.TimeUnit

object TimeUtility {

    fun formatTimeToHoursMinutesSecondsFrom(milliseconds: Long) : String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60

        // Create double digit value
        val stringSeconds = if (seconds < 10) "0$seconds" else seconds.toString()

        if (hours == 0L) {
            return "$minutes:$stringSeconds"
        }

        // Create double digit value
        val stringMinutes = if (minutes < 10) "0$minutes" else minutes.toString()
        return "$hours:$stringMinutes:$stringSeconds"
    }

}