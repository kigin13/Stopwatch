package com.timers.stopwatch.core.data

import com.timers.stopwatch.core.data.repository.PomodoroRepository
import com.timers.stopwatch.core.data.repository.RunningSchedulerRepo
import com.timers.stopwatch.core.data.repositoryImp.PomodoroRepositoryImp
import com.timers.stopwatch.core.data.repositoryImp.RunningSchedulerRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Patrice Mulindi email(mulindipatrice00@gmail.com) on 08.12.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsPomodoroRepository(pomodoroRepositoryImp: PomodoroRepositoryImp): PomodoroRepository

    @Binds
    fun bindsRunningScheduleRepository(runningSchedulerRepoImpl: RunningSchedulerRepoImpl): RunningSchedulerRepo
}
