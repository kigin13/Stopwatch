package com.timers.stopwatch.core.data.source.impl.dataProvider

import com.timers.stopwatch.core.data.source.impl.converter.asEntity
import com.timers.stopwatch.core.data.source.impl.converter.asModel
import com.timers.stopwatch.core.database.dao.RunningScheduleDao
import com.timers.stopwatch.core.model.PomodoroEnum
import com.timers.stopwatch.core.model.PomodoroRunningScheduler
import com.timers.stopwatch.core.model.PomodoroStatus
import com.timers.stopwatch.core.model.Time
import javax.inject.Inject

class RunningSchedulerProvider @Inject constructor(
    private val scheduleDao: RunningScheduleDao
) {
    suspend fun insertRunningSchedule(scheduler: PomodoroRunningScheduler): Long {
        return scheduleDao.insertRunningSchedule(scheduler.asEntity())
    }

    suspend fun getPomodoroCount(): Int {
        return scheduleDao.getPomodoroCount(
            PomodoroEnum.FOCUS.name,
            PomodoroStatus.COMPLETED.name
        )
    }

    suspend fun getStartTime(): Time {
        return scheduleDao.getStartTime().asModel()
    }

    suspend fun updateRunningSchedule(id: Int, status: String) {
        scheduleDao.updateRunningSchedule(id, status)
    }

    suspend fun deleteRunningSchedule() {
        scheduleDao.deleteRunningSchedule()
    }
}