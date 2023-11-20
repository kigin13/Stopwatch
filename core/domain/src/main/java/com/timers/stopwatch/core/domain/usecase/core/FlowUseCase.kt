package com.timers.stopwatch.core.domain.usecase.core

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<out Type : Any, in Params : UseCase.Params> : UseCase<Type, Params> {

    abstract fun run(params: Params): Flow<Type>

    operator fun invoke(params: Params): Flow<Type> {
        return run(params)
    }
}
