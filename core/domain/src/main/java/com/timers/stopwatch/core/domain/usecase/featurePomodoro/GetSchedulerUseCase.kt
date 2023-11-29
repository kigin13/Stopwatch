package com.timers.stopwatch.core.domain.usecase.featurePomodoro

import com.timers.stopwatch.core.domain.repository.PomodoroRepository
import javax.inject.Inject

class GetSchedulerUseCase @Inject constructor(
    private val repo : PomodoroRepository
) {

    suspend operator fun invoke() =
        repo.getPomodoroSchedules()
}