package com.newage.feature.pomodoro.di

import com.newage.feature.pomodoro.useCase.TimerIndicatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTimerIndicatorUseCase() = TimerIndicatorUseCase()
}