package com.timers.stopwatch.core.data.repositoryImpl

import com.timers.stopwatch.core.data.source.impl.dataProvider.PomodoroDataProvider
import com.timers.stopwatch.core.domain.repository.PomodoroRepository
import com.timers.stopwatch.core.model.PomodoroScheduleModel
import javax.inject.Inject

class PomodoroRepositoryImp @Inject constructor(
    private val provider: PomodoroDataProvider
) : PomodoroRepository {
    override suspend fun getPomodoroSchedules(): List<PomodoroScheduleModel> {
        return provider.getPomodoroSchedules()
    }

    override suspend fun updateSchedule(model: PomodoroScheduleModel) {
        provider.updateSchedule(model)
    }

    override suspend fun resetSchedulers() {
        provider.resetSchedulers()
    }
}