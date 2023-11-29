package com.timers.stopwatch.core.database.model

data class TimeEntity(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0
) {
    fun getSeconds(): Long =
        ((((hours * 60) + minutes) * 60) + seconds).toLong()
}