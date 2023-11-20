package com.timers.stopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.timers.stopwatch.databinding.ActivityAppBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
@AndroidEntryPoint
class StopwatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(ActivityAppBinding.inflate(layoutInflater).root)
    }
}
