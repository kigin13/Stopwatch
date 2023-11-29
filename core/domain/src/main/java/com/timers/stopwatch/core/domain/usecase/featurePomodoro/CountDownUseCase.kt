package com.timers.stopwatch.core.domain.usecase.featurePomodoro

import com.timers.stopwatch.core.domain.usecase.core.FlowNoneParamsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CountDownUseCase : FlowNoneParamsUseCase<Int>() {

    private var countDownFrom = 4
    override fun run(): Flow<Int> {
        return flow {
            while (countDownFrom >= 0) {
                delay(1000L)
                emit(countDownFrom)
                countDownFrom--
            }
        }
    }
}