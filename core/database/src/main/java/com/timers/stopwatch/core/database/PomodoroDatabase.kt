package com.timers.stopwatch.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timers.stopwatch.core.database.dao.PomodoroScheduleDao
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity

@Database(
    entities = [PomodoroScheduleEntity::class],
    version = 1
)
abstract class PomodoroDatabase: RoomDatabase() {
    abstract fun pomodoroScheduleDao(): PomodoroScheduleDao

}