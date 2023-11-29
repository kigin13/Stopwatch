package com.timers.stopwatch.core.model

data class PomodoroRunningScheduler(
    val id: Int? = null,
    val pomodoro: Int = 0,
    val round: Int = 0,
    val title: PomodoroEnum,
    val duration: Time,
    val status: PomodoroStatus = PomodoroStatus.ADDED,
    val startTime: Time = Time()
)