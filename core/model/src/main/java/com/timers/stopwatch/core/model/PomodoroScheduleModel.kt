package com.timers.stopwatch.core.model


data class PomodoroScheduleModel(
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