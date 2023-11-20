package com.timers.stopwatch.core.domain.usecase.core

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
interface NoneParamsUseCase<out Type : Any> : UseCase<Type, UseCase.None>
