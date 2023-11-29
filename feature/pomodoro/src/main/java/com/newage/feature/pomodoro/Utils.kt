package com.newage.feature.pomodoro

import android.view.View
import android.widget.EditText
import java.text.DecimalFormat

fun Boolean.getVisibility(): Int =
    if(this) View.VISIBLE else View.GONE

fun Int.toTimeString(): String =
    DecimalFormat("00").format(this)

fun EditText.replaceIfEmpty(defaultText: String) {
    if (this.text.isNullOrEmpty()) {
        this.setText(defaultText)
    }
}