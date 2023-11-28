package com.timers.stopwatch.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timers.stopwatch.core.database.constants.PomodoroDbConstants.POMODORO_SCHEDULE

@Entity(tableName = POMODORO_SCHEDULE)
data class PomodoroScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val hours: Int,
    val minutes: Int,
    val isPomodoro: Boolean,
    val longBreakAfter: Int
) {
    constructor(title: String, hours: Int, minutes: Int, isPomodoro: Boolean, longBreakAfter: Int)
            : this(null, title, hours, minutes, isPomodoro, longBreakAfter)
}
