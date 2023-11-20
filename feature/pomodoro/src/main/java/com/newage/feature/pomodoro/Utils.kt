package com.newage.feature.pomodoro

import android.view.View

fun Boolean.getVisibility(): Int =
    if(this) View.VISIBLE else View.GONE