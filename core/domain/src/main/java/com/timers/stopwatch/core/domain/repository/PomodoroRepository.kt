package com.timers.stopwatch.core.domain.repository

import com.timers.stopwatch.core.model.PomodoroScheduleModel

interface PomodoroRepository {

    suspend fun getPomodoroSchedules() : List<PomodoroScheduleModel>

    suspend fun updateSchedule(model: PomodoroScheduleModel)

    suspend fun resetSchedulers()
}