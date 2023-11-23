package com.timers.stopwatch.core.data.repositoryImp

import com.timers.stopwatch.core.data.model.PomodoroEnum
import com.timers.stopwatch.core.data.model.PomodoroStatus
import com.timers.stopwatch.core.data.repository.RunningSchedulerRepo
import com.timers.stopwatch.core.database.dao.RunningScheduleDao
import com.timers.stopwatch.core.database.model.RunningScheduleEntity
import com.timers.stopwatch.core.database.model.Time
import javax.inject.Inject

class RunningSchedulerRepoImpl @Inject constructor(
    private val scheduleDao: RunningScheduleDao
) : RunningSchedulerRepo {
    override suspend fun insertRunningSchedule(scheduler: RunningScheduleEntity): Long {
        return scheduleDao.insertRunningSchedule(scheduler)
    }

    override suspend fun getPomodoroCount(): Int {
        return scheduleDao.getPomodoroCount(
            PomodoroEnum.FOCUS.name,
            PomodoroStatus.COMPLETED.name
        )
    }

    override suspend fun getStartTime(): Time? {
        return scheduleDao.getStartTime()
    }

    override suspend fun updateRunningSchedule(id: Int, status: String) {
        scheduleDao.updateRunningSchedule(id, status)
    }

    override suspend fun deleteRunningSchedule() {
        scheduleDao.deleteRunningSchedule()
    }
}