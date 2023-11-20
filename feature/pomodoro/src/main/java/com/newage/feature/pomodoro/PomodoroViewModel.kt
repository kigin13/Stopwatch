package com.newage.feature.pomodoro

import com.timers.stopwatch.core.common.android.StopwatchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 01.11.2023.
 */
@HiltViewModel
class PomodoroViewModel @Inject constructor() : StopwatchViewModel()
