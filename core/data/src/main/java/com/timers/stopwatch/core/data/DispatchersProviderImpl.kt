package com.timers.stopwatch.core.data

import com.timers.stopwatch.core.domain.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
class DispatchersProviderImpl @Inject constructor() : DispatchersProvider {
    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
