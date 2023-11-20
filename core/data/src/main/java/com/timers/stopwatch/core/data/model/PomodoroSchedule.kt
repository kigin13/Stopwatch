package com.timers.stopwatch.core.data.model

import com.timers.stopwatch.core.data.source.api.model.PomodoroScheduleModel
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity

fun PomodoroScheduleModel.asEntity() =
    PomodoroScheduleEntity(
        title = title,
        hours = hours,
        minutes = minutes,
        isPomodoro = isPomodoro,
        longBreakAfter = longBreakAfter
    )