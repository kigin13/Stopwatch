package com.timers.stopwatch.core.database

import androidx.room.TypeConverter
import com.timers.stopwatch.core.database.model.TimeEntity

class TimeTypeConverter {
    @TypeConverter
    fun timeToString(time: TimeEntity): String {
        return time.run { "$hours:$minutes:$seconds" }
    }

    @TypeConverter
    fun stringToTime(time: String): TimeEntity {
        val time = time.split(":").toList().map { it.toInt() }
        return TimeEntity(time[0], time[1], time[2])
    }
}