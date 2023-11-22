package com.newage.feature.pomodoro

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.newage.feature.pomodoro.Constants.TIMER_BROADCAST_ACTION
import com.newage.feature.pomodoro.model.TimerIndicatorModel
import com.timers.stopwatch.feature.pomodoro.R

class TimerBackgroundService : Service() {

    private var startPomodoro = false

    var remainingTimeSeconds = 0
    val totalSeconds = 0

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        val id = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) notificationChannelId else ""
        val notification: Notification = NotificationCompat.Builder(this, id)
            .setSmallIcon(R.drawable.play)
            .setContentTitle("Pomodoro Round")
            .build()
        startForeground(11, notification)
    }

    @get:RequiresApi(api = Build.VERSION_CODES.O)
    private val notificationChannelId: String
        private get() {
            val channel = NotificationChannel(
                "Pomodoro",
                "Pomodoro",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            return channel.id
        }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startPomodoro = true

        countdownTimer(0L)

        return START_STICKY
    }

    private fun countdownTimer(timeSeconds: Long) =
        object : CountDownTimer(timeSeconds * 1000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(p0: Long) {
                val percentagePassed = (remainingTimeSeconds.toDouble() / totalSeconds) * 100
                val roundedPercentage = percentagePassed.toFloat()
                val remainingPercentage = 100 - roundedPercentage
                remainingTimeSeconds -= 1

                val intent = Intent(TIMER_BROADCAST_ACTION).apply {
//                    putExtra(
//                        "pomodoroData",
//                        currentTimeModel(remainingPercentage)
//                    )
                }
                applicationContext.sendBroadcast(intent)
            }
        }

    private fun calculateSeconds(currentTime: TimerIndicatorModel): Int =
        ((currentTime.hours * 60) + currentTime.minutes) * 60

    private fun currentTimeModel(percentage: Float): TimerIndicatorModel {
        val time = calculateCurrentTime()
        return TimerIndicatorModel(
            hours = time.first,
            minutes = time.second,
            seconds = time.third,
            percentage = percentage
        )
    }

    private fun calculateCurrentTime(): Triple<Int, Int, Int> {
        val hours = remainingTimeSeconds / 3600
        val remainingSecondsAfterHours = remainingTimeSeconds % 3600
        val minutes = remainingSecondsAfterHours / 60
        val seconds = remainingSecondsAfterHours % 60
        return Triple(hours, minutes, seconds)
    }
}