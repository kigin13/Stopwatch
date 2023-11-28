package com.timers.stopwatch.core.database.di

import android.content.Context
import androidx.room.Room
import com.timers.stopwatch.core.database.PomodoroDatabase
import com.timers.stopwatch.core.database.constants.PomodoroDbConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Patrice Mulindi email(mulindipatrice00@gmail.com) on 21.12.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun roomInstance(
        @ApplicationContext context: Context
    ): PomodoroDatabase =
        Room.databaseBuilder(
            context, PomodoroDatabase::class.java, PomodoroDbConstants.PROMODORO_DB
        )
        .fallbackToDestructiveMigration()
        .build()
}