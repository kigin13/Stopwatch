package com.timers.stopwatch.core.common.android.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/27/2023.
 */
fun Fragment.setResult(key: String, bundle: Bundle? = null) {
    requireActivity().supportFragmentManager.setFragmentResult(
        key,
        bundle ?: Bundle(),
    )
}

fun Fragment.addResultListener(key: String, listener: (bundle: Bundle) -> Unit) {
    requireActivity().supportFragmentManager.setFragmentResultListener(
        key,
        this,
    ) { _, bundle ->
        listener(bundle)
    }
}

fun FragmentActivity.setResult(key: String, bundle: Bundle? = null) {
    supportFragmentManager.setFragmentResult(
        key,
        bundle ?: Bundle(),
    )
}

fun FragmentActivity.addResultListener(key: String, listener: (bundle: Bundle) -> Unit) {
    supportFragmentManager.setFragmentResultListener(
        key,
        this,
    ) { _, bundle ->
        listener(bundle)
    }
}
