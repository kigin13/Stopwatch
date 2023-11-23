package com.timers.stopwatch.core.data.model

import com.timers.stopwatch.core.database.model.RunningScheduleEntity

data class PomodoroScheduler(
    val id: Int? = null,
    val pomodoro: Int = 0,
    val round: Int = 0,
    val title: PomodoroEnum,
    val duration: Time,
    val status: PomodoroStatus = PomodoroStatus.ADDED,
    val startTime: Time = Time()
) {
    fun asEntity() = RunningScheduleEntity(
        pomodoro = pomodoro,
        startTime = startTime.asEntity(),
        status = status.name,
        title = title.name,
        id = id,
        duration = duration.asEntity(),
        round = round
    )
}

