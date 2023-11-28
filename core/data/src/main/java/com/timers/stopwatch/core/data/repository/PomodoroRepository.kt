package com.timers.stopwatch.core.data.repository

import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity

interface PomodoroRepository {

    suspend fun getPomodoroSchedules() : List<PomodoroScheduleEntity>

    suspend fun syncPomodoroSchedules()

    suspend fun updateSchedule(model: PomodoroScheduleEntity)

    suspend fun resetSchedulers()
}