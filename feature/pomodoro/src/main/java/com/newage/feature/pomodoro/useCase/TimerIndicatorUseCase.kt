package com.newage.feature.pomodoro.useCase

import com.newage.feature.pomodoro.model.TimerIndicatorModel
import com.timers.stopwatch.core.domain.usecase.core.FlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TimerIndicatorUseCase: FlowUseCase<TimerIndicatorModel, TimerIndicatorModel>() {

    private var totalSeconds: Int = 0
    private var remainingTimeSeconds: Int = 0
    override fun run(params: TimerIndicatorModel): Flow<TimerIndicatorModel> {
        totalSeconds = calculateSeconds(params)
        remainingTimeSeconds = totalSeconds

        return flow {
            while (remainingTimeSeconds >= 0) {
                val percentagePassed = (remainingTimeSeconds.toDouble() / totalSeconds) * 100
                val roundedPercentage = percentagePassed.toFloat()
                val remainingPercentage = 100 - roundedPercentage
                remainingTimeSeconds -= 1

                delay(1000)
                val time = calculateCurrentTime(remainingTimeSeconds)
                emit(currentTimeModel(remainingPercentage, time))
            }
        }
    }

    fun calculateSeconds(currentTime: TimerIndicatorModel): Int =
        ((currentTime.hours * 60) + currentTime.minutes) * 60

    fun currentTimeModel(percentage: Float, time: Triple<Int, Int, Int>): TimerIndicatorModel {
        return TimerIndicatorModel(
            hours = time.first,
            minutes = time.second,
            seconds = time.third,
            percentage = percentage
        )
    }

    fun calculateCurrentTime(remainingTime: Int): Triple<Int, Int, Int> {
        val hours = remainingTime / 3600
        val remainingSecondsAfterHours = remainingTime % 3600
        val minutes = remainingSecondsAfterHours / 60
        val seconds = remainingSecondsAfterHours % 60
        return Triple(hours, minutes, seconds)
    }
}