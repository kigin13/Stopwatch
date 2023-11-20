package com.newage.feature.pomodoro

sealed class SchedulerItemCallback {
    data class OnTimeChange(val position: Int, val newTime: Pair<Int, Int>) :
        SchedulerItemCallback()

    data class OnPomodoroCountChange(val position: Int, val pomodoroCount: Int) :
        SchedulerItemCallback()

    data class OnFocusChanged(val position: Int) : SchedulerItemCallback()
}
