package com.newage.feature.pomodoro.useCase

import com.timers.stopwatch.core.data.repository.PomodoroRepository
import javax.inject.Inject

class GetSchedulerUseCase @Inject constructor(
    private val repo : PomodoroRepository
) {

    suspend operator fun invoke() =
        repo.getPomodoroSchedules()
}