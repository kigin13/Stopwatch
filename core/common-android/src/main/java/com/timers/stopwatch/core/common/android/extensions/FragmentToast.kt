
@file:Suppress("ktlint:standard:filename")

package com.timers.stopwatch.core.common.android.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 29.11.2022.
 */
fun Fragment.showToastShort(message: String) {
    if (message.isBlank() || isAdded.not()) return
    lifecycleScope.launch {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}

fun Fragment.showToastShort(@StringRes message: Int) {
    if (isAdded.not()) return
    lifecycleScope.launch {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
