package com.timers.stopwatch

import android.app.Application
import com.timers.stopwatch.core.data.RepositoryModule
import com.timers.stopwatch.core.data.repository.PomodoroRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
@HiltAndroidApp
class StopwatchApplication : Application() {
    @Inject
    lateinit var repo : PomodoroRepository

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        CoroutineScope(Dispatchers.Default).launch {
            repo.syncPomodoroSchedules()
        }
    }
}
