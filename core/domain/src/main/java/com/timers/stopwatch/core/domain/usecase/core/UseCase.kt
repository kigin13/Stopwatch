package com.timers.stopwatch.core.domain.usecase.core

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
interface UseCase<out Type, in Params : UseCase.Params> where Type : Any {

    interface Params

    object None : Params
}
