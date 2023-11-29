package com.timers.stopwatch.core.data.repositoryImpl

import com.timers.stopwatch.core.data.source.impl.dataProvider.RunningSchedulerProvider
import com.timers.stopwatch.core.domain.repository.RunningSchedulerRepo
import com.timers.stopwatch.core.model.PomodoroRunningScheduler
import com.timers.stopwatch.core.model.Time
import javax.inject.Inject

class RunningSchedulerRepoImpl @Inject constructor(
    private val runningSchedulerProvider: RunningSchedulerProvider
) : RunningSchedulerRepo {
    override suspend fun insertRunningSchedule(scheduler: PomodoroRunningScheduler): Long {
        return runningSchedulerProvider.insertRunningSchedule(scheduler)
    }

    override suspend fun getPomodoroCount(): Int {
        return runningSchedulerProvider.getPomodoroCount()
    }

    override suspend fun getStartTime(): Time {
        return runningSchedulerProvider.getStartTime()
    }

    override suspend fun updateRunningSchedule(id: Int, status: String) {
        runningSchedulerProvider.updateRunningSchedule(id, status)
    }

    override suspend fun deleteRunningSchedule() {
        runningSchedulerProvider.deleteRunningSchedule()
    }
}