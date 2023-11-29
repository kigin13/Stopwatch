package com.timers.stopwatch.core.data.source.impl.converter

import com.timers.stopwatch.core.database.model.RunningScheduleEntity
import com.timers.stopwatch.core.model.PomodoroEnum
import com.timers.stopwatch.core.model.PomodoroRunningScheduler
import com.timers.stopwatch.core.model.PomodoroStatus

fun PomodoroRunningScheduler.asEntity() =
    RunningScheduleEntity(
        id = this.id,
        pomodoro = this.pomodoro,
        round = this.round,
        title = this.title.name,
        duration = this.duration.asEntity(),
        status = this.status.name,
        startTime = this.startTime.asEntity()
    )

fun RunningScheduleEntity.asModel() =
    PomodoroRunningScheduler(
        id = this.id,
        pomodoro = this.pomodoro,
        round = this.round,
        title = PomodoroEnum.valueOf(this.title),
        duration = this.duration.asModel(),
        status = PomodoroStatus.valueOf(this.status),
        startTime = this.startTime.asModel()
    )