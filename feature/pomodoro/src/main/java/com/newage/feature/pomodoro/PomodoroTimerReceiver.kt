package com.newage.feature.pomodoro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PomodoroTimerReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(Constants.TIMER_BROADCAST_ACTION)) {

        }
    }
}