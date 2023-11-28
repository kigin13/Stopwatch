package com.timers.stopwatch.core.data.repositoryImp

import com.timers.stopwatch.core.data.model.asEntity
import com.timers.stopwatch.core.data.repository.PomodoroRepository
import com.timers.stopwatch.core.data.source.api.DataSource
import com.timers.stopwatch.core.data.source.api.model.PomodoroScheduleModel
import com.timers.stopwatch.core.database.dao.PomodoroScheduleDao
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
import javax.inject.Inject

class PomodoroRepositoryImp @Inject constructor(
    private val pomodoroDao: PomodoroScheduleDao,
    private val dataSource: DataSource
) : PomodoroRepository {
    override suspend fun getPomodoroSchedules(): List<PomodoroScheduleEntity> {
        return pomodoroDao.getPromodoroSchedule();
    }

    override suspend fun syncPomodoroSchedules() {
        val defaultSchedules = dataSource.getDefaultSchedule();
        if (pomodoroDao.getPromodoroSchedule().isEmpty()) {
            pomodoroDao.insertPromodoroSchedule(defaultSchedules.map(PomodoroScheduleModel::asEntity))
        }
    }

    override suspend fun updateSchedule(model: PomodoroScheduleEntity) {
        pomodoroDao.updateScheduler(model)
    }

    override suspend fun resetSchedulers() {
        pomodoroDao.resetSchedulers()
    }
}