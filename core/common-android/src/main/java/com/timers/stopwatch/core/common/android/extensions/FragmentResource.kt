package com.timers.stopwatch.core.common.android.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.timers.stopwatch.core.common.android.Resource
import com.timers.stopwatch.core.common.android.ResourceEvent

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 10.04.2022.
 */
inline fun <T : Any> Fragment.createResourceObserver(
    crossinline onLoading: () -> Unit = { },
    crossinline onHideLoading: () -> Unit = { },
    crossinline onError: (String, Throwable) -> Unit = { message, _ -> showToastShort(message) },
    crossinline onSuccess: (T) -> Unit = {},
): Observer<Resource<T>> = Observer { resource ->
    handleResource(resource, onLoading, onError, onHideLoading, onSuccess)
}

@Suppress("unused")
inline fun Fragment.createUnitResourceObserver(
    crossinline onLoading: () -> Unit = {},
    crossinline onHideLoading: () -> Unit = {},
    crossinline onError: (String, Throwable) -> Unit = { message, _ -> showToastShort(message) },
    crossinline onSuccess: () -> Unit = {},
): Observer<Resource<Unit>> = Observer { resource ->
    handleUnitResource(resource, onLoading, onError, onHideLoading, onSuccess)
}

inline fun <T : Any> Fragment.createResourceEventObserver(
    crossinline onLoading: () -> Unit = {},
    crossinline onHideLoading: () -> Unit = {},
    crossinline onError: (String, Throwable) -> Unit = { message, _ -> showToastShort(message) },
    crossinline onSuccess: (T) -> Unit = {},
): Observer<ResourceEvent<T>> = Observer { event ->
    val resource = event.getContentIfNotHandled()

    // an event should be handled only once
    if (resource != null) {
        handleResource(resource, onLoading, onError, onHideLoading, onSuccess)
    }
}

inline fun Fragment.createUnitResourceEventObserver(
    crossinline onLoading: () -> Unit = {},
    crossinline onHideLoading: () -> Unit = {},
    crossinline onError: (String, Throwable) -> Unit = { message, _ -> showToastShort(message) },
    crossinline onSuccess: () -> Unit = {},
): Observer<ResourceEvent<Unit>> = Observer { event ->
    val resource = event.getContentIfNotHandled()

    // an event should be handled only once
    if (resource != null) {
        handleUnitResource(resource, onLoading, onError, onHideLoading, onSuccess)
    }
}
