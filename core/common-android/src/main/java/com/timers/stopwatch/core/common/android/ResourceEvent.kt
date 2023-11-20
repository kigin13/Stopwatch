package com.timers.stopwatch.core.common.android

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
sealed class ResourceEvent<out T : Any>(resource: Resource<T>) : Event<Resource<T>>(resource) {

    object Loading : ResourceEvent<Nothing>(Resource.Loading)
    data class Success<out T : Any>(val data: T) : ResourceEvent<T>(Resource.Success(data))
    data class Error(
        val message: String?,
        val exception: Throwable,
    ) : ResourceEvent<Nothing>(Resource.Error(message, exception))
}
