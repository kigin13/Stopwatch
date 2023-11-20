package com.timers.stopwatch.core.data.source.api

import com.timers.stopwatch.core.data.source.api.model.PomodoroScheduleModel

class DataSource {

    fun getDefaultSchedule(): List<PomodoroScheduleModel> {
        return listOf(
            PomodoroScheduleModel(
                "Focus duration", 0,  0, false, 0
            ),
            PomodoroScheduleModel(
                "Short break duration", 0, 0, false, 0
            ),
            PomodoroScheduleModel(
                "Long break duration", 0, 0, false, 0
            ),
            PomodoroScheduleModel(
                "Long break after", 0, 0, true, 1
            ),
        )
    }
}