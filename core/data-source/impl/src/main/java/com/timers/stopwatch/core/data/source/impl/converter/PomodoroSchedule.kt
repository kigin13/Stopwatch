package com.timers.stopwatch.core.data.source.impl.converter

import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
import com.timers.stopwatch.core.model.PomodoroScheduleModel

fun PomodoroScheduleModel.asEntity() =
    PomodoroScheduleEntity(
        id = this.id,
        title = this.title,
        hours = this.hours,
        minutes = this.minutes,
        isPomodoro = this.isPomodoro,
        longBreakAfter = this.longBreakAfter
    )

fun PomodoroScheduleEntity.asModel() =
    PomodoroScheduleModel(
        id = this.id,
        title = this.title,
        hours = this.hours,
        minutes = this.minutes,
        isPomodoro = this.isPomodoro,
        longBreakAfter = this.longBreakAfter
    )