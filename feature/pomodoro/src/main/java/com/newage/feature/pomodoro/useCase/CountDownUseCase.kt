package com.newage.feature.pomodoro.useCase

import com.timers.stopwatch.core.domain.usecase.core.FlowNoneParamsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountDownUseCase : FlowNoneParamsUseCase<Int>() {

    private var countDownFrom = 5
    override fun run(): Flow<Int> {
        return flow {
            while (countDownFrom >= 0) {
                delay(1000L)
                countDownFrom--
                emit(countDownFrom)
            }
        }
    }
}