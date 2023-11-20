package com.timers.stopwatch.core.data

import com.timers.stopwatch.core.domain.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface DispatchersModule {

    @Binds
    @Singleton
    fun bindDispatchersProvider(provider: DispatchersProviderImpl): DispatchersProvider
}
