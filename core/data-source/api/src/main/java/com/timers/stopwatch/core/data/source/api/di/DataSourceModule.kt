package com.timers.stopwatch.core.data.source.api.di

import com.timers.stopwatch.core.data.source.api.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun buildDataSource():DataSource = DataSource()
}