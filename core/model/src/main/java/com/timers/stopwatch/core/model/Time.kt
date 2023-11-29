package com.timers.stopwatch.core.model

data class Time(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0
) {
    fun getSeconds(): Long =
        ((((hours * 60) + minutes) * 60) + seconds).toLong()
}