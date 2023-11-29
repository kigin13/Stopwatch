package com.timers.stopwatch.core.data.source.impl.dataProvider

import com.timers.stopwatch.core.data.source.api.DataSource
import com.timers.stopwatch.core.data.source.impl.converter.asEntity
import com.timers.stopwatch.core.data.source.impl.converter.asModel
import com.timers.stopwatch.core.database.dao.PomodoroScheduleDao
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
import com.timers.stopwatch.core.model.PomodoroScheduleModel
import javax.inject.Inject

class PomodoroDataProvider @Inject constructor(
    private val schedulerDao: PomodoroScheduleDao
) {

    suspend fun getPomodoroSchedules(): List<PomodoroScheduleModel> {
        var schedules = schedulerDao.getPromodoroSchedule()
        if (schedules.isEmpty()) {
            val defaultSchedules = DataSource.getDefaultSchedule()
            schedulerDao.insertPromodoroSchedule(defaultSchedules.map(PomodoroScheduleModel::asEntity))
            schedules = schedulerDao.getPromodoroSchedule()
        }
        return schedules.map(PomodoroScheduleEntity::asModel);
    }

    suspend fun updateSchedule(model: PomodoroScheduleModel) {
        schedulerDao.updateScheduler(model.asEntity())
    }

    suspend fun resetSchedulers() {
        schedulerDao.resetSchedulers()
    }
}