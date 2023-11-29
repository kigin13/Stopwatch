package com.timers.stopwatch.core.domain.repository

import com.timers.stopwatch.core.model.PomodoroRunningScheduler
import com.timers.stopwatch.core.model.Time

interface RunningSchedulerRepo {

    suspend fun insertRunningSchedule(scheduler: PomodoroRunningScheduler): Long

    suspend fun getPomodoroCount(): Int

    suspend fun getStartTime(): Time?

    suspend fun updateRunningSchedule(id: Int, status: String)

    suspend fun deleteRunningSchedule()
}