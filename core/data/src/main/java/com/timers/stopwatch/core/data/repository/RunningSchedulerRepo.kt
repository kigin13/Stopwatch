package com.timers.stopwatch.core.data.repository

import com.timers.stopwatch.core.database.model.RunningScheduleEntity
import com.timers.stopwatch.core.database.model.Time

interface RunningSchedulerRepo {

    suspend fun insertRunningSchedule(scheduler: RunningScheduleEntity): Long

    suspend fun getPomodoroCount(): Int

    suspend fun getStartTime(): Time?

    suspend fun updateRunningSchedule(id: Int, status: String)

    suspend fun deleteRunningSchedule()
}