package com.timers.stopwatch.core.common.android.extensions

import androidx.fragment.app.DialogFragment

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/17/2023.
 * Set the width of the [DialogFragment] to a percentage of the screen width
 * Call it onStart() of the [DialogFragment]
 */
fun DialogFragment.setDialogWidthInPercent(percent: Float) {
    val window = dialog?.window ?: return
    val width = (resources.displayMetrics.widthPixels * percent).toInt()
    window.setLayout(width, window.attributes.height)
}
