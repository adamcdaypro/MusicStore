package com.example.musicstore.utilities

import org.junit.Test

class TimeUtilityTest {

    companion object {

        private const val ONE_SECOND = 1000L
        private const val ONE_MINUTE = ONE_SECOND * 60L
        private const val ONE_HOUR = ONE_MINUTE * 60L
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_zeroSeconds() {
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(0L) == "0:00")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_leadingZeroSeconds() {
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(ONE_SECOND) == "0:01")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_trailingZeroSeconds() {
        val tenSeconds = ONE_SECOND * 10L
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(tenSeconds) == "0:10")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_leadingZeroMinutes() {
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(ONE_MINUTE) == "1:00")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_trailingZeroMinutes() {
        val tenMinutes = ONE_MINUTE * 10L
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(tenMinutes) == "10:00")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_leadingZeroHours() {
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(ONE_HOUR) == "1:00:00")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_trailingZeroHours() {
        val tenHours = ONE_HOUR * 10L
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(tenHours) == "10:00:00")
    }

    @Test
    fun formatTimeToHoursMinutesSecondsFrom_allDoubleDigits() {
        val time = ONE_HOUR * 12L + ONE_MINUTE * 34 + ONE_SECOND * 56
        assert(TimeUtility.formatTimeToHoursMinutesSecondsFrom(time) == "12:34:56")
    }
}