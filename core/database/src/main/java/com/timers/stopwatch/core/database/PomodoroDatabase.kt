package com.timers.stopwatch.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timers.stopwatch.core.database.dao.PomodoroScheduleDao
import com.timers.stopwatch.core.database.dao.RunningScheduleDao
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
import com.timers.stopwatch.core.database.model.RunningScheduleEntity

@Database(
    entities = [PomodoroScheduleEntity::class, RunningScheduleEntity::class],
    version = 1
)
@TypeConverters(
    TimeTypeConverter::class
)
abstract class PomodoroDatabase: RoomDatabase() {
    abstract fun pomodoroScheduleDao(): PomodoroScheduleDao
    abstract fun runningScheduleDao(): RunningScheduleDao

}