package com.newage.feature.pomodoro.model

import com.timers.stopwatch.core.domain.usecase.core.UseCase

data class TimerIndicatorModel(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val percentage: Float = 0F
)