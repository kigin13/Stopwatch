package com.timers.stopwatch.core.domain

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
interface DispatchersProvider {

    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun default(): CoroutineDispatcher
}
