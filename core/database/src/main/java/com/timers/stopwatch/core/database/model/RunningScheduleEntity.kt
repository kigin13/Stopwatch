package com.timers.stopwatch.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timers.stopwatch.core.database.constants.PomodoroDbConstants.RUNNING_SCHEDULE

@Entity(tableName = RUNNING_SCHEDULE)
data class RunningScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val pomodoro: Int,
    val round: Int,
    val title: String,
    val duration: Time,
    val status: String,
    val startTime: Time
)