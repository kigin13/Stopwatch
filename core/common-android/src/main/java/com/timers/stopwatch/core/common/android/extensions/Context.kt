package com.timers.stopwatch.core.common.android.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/5/2023.
 */
fun Context.getThemeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}
