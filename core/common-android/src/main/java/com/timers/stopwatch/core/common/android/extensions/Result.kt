package com.timers.stopwatch.core.common.android.extensions

import com.timers.stopwatch.core.common.android.Resource
import com.timers.stopwatch.core.common.android.ResourceEvent

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 12.12.2022.
 */
fun <T : Any> Result<T>.toResource(): Resource<T> = fold(
    ::mapToResourceSuccess,
    ::mapToResourceError,
)

private fun <T : Any> mapToResourceSuccess(data: T): Resource<T> {
    return Resource.Success(data)
}

private fun <T : Any> mapToResourceError(throwable: Throwable): Resource<T> {
    return Resource.Error(throwable.message, throwable)
}

fun <T : Any> Result<T>.toResourceEvent(): ResourceEvent<T> = fold(
    ::mapToResourceEventSuccess,
    ::mapToResourceEventError,
)

private fun <T : Any> mapToResourceEventSuccess(data: T): ResourceEvent<T> {
    return ResourceEvent.Success(data)
}

private fun <T : Any> mapToResourceEventError(throwable: Throwable): ResourceEvent<T> {
    return ResourceEvent.Error(throwable.message, throwable)
}
