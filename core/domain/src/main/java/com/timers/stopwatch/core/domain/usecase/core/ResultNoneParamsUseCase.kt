package com.timers.stopwatch.core.domain.usecase.core

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
abstract class ResultNoneParamsUseCase<out Type : Any> : NoneParamsUseCase<Type> {
    abstract suspend operator fun invoke(): Result<Type>
}
