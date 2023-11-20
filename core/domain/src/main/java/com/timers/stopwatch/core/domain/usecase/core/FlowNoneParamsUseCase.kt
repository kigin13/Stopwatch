package com.timers.stopwatch.core.domain.usecase.core

import kotlinx.coroutines.flow.Flow

abstract class FlowNoneParamsUseCase<out Type : Any> : NoneParamsUseCase<Type> {

    abstract fun run(): Flow<Type>

    operator fun invoke(): Flow<Type> {
        return run()
    }
}
