package com.newage.feature.pomodoro.model

data class PomodoroScheduler(
    val round: Int = 0,
    val title: String,
    val duration: Time,
    val status: PomodoroStatus = PomodoroStatus.ADDED,
    val startTime: Time = Time()
)

data class Time(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0
)