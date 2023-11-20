package com.timers.stopwatch.core.common.android.extensions

import com.timers.stopwatch.core.common.android.Resource

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 29.11.2022.
 */
inline fun <T : Any> handleResource(
    resource: Resource<T>,
    crossinline onLoading: () -> Unit,
    crossinline onError: (String, Throwable) -> Unit,
    crossinline onHideLoading: () -> Unit,
    crossinline onSuccess: (T) -> Unit,
) = when (resource) {
    Resource.Loading -> onLoading()
    is Resource.Error -> {
        onError(resource.message ?: "", resource.exception)
        onHideLoading()
    }
    is Resource.Success -> {
        onSuccess(resource.data)
        onHideLoading()
    }
}

inline fun handleUnitResource(
    resource: Resource<Unit>,
    crossinline onLoading: () -> Unit,
    crossinline onError: (String, Throwable) -> Unit,
    crossinline onHideLoading: () -> Unit,
    crossinline onSuccess: () -> Unit,
) = when (resource) {
    Resource.Loading -> onLoading()
    is Resource.Error -> {
        onError(resource.message ?: "", resource.exception)
        onHideLoading()
    }
    is Resource.Success -> {
        onSuccess()
        onHideLoading()
    }
}
