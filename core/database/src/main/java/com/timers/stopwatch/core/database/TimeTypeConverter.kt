package com.timers.stopwatch.core.database

import androidx.room.TypeConverter
import com.timers.stopwatch.core.database.model.Time

class TimeTypeConverter {
    @TypeConverter
    fun timeToString(time: Time): String {
        return time.run { "$hours:$minutes:$seconds" }
    }

    @TypeConverter
    fun stringToTime(time: String): Time {
        val time = time.split(":").toList().map { it.toInt() }
        return Time(time[0], time[1], time[2])
    }
}