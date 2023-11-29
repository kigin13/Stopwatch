package com.timers.stopwatch.core.domain.usecase.featurePomodoro

object PomodoroCountChangeUseCase {

    fun getNewCount(oldCount: Int, isIncrement: Boolean): Int {
        return when {
            isIncrement -> oldCount + 1
            oldCount <= 1 -> oldCount
            else -> oldCount - 1
        }
    }
}