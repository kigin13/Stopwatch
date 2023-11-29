package com.timers.stopwatch.core.database.di

import com.timers.stopwatch.core.database.PomodoroDatabase
import com.timers.stopwatch.core.database.dao.PomodoroScheduleDao
import com.timers.stopwatch.core.database.dao.RunningScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Patrice Mulindi email(mulindipatrice00@gmail.com) on 21.12.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun pomodoroScheduleDao(database: PomodoroDatabase): PomodoroScheduleDao =
        database.pomodoroScheduleDao()

    @Singleton
    @Provides
    fun pomodoroRunningScheduleDao(database: PomodoroDatabase): RunningScheduleDao =
        database.runningScheduleDao()
}
