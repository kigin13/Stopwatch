package com.timers.stopwatch.core.domain.usecase.core

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
abstract class ResultUseCase<out Type : Any, in Params : UseCase.Params> : UseCase<Type, Params> {
    abstract suspend operator fun invoke(params: Params): Result<Type>
}
