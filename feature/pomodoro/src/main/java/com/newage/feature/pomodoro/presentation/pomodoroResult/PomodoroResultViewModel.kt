package com.newage.feature.pomodoro.presentation.pomodoroResult

import androidx.lifecycle.viewModelScope
import com.timers.stopwatch.core.common.android.StopwatchViewModel
import com.timers.stopwatch.core.data.repository.RunningSchedulerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PomodoroResultViewModel @Inject constructor(
    private val schedulerRepo: RunningSchedulerRepo
) : StopwatchViewModel() {


    private val _pomodoroTomato = MutableStateFlow(0)
    val pomodoroTomato = _pomodoroTomato.asStateFlow()

    private val _startTime = MutableStateFlow("")
    val startTime = _startTime.asStateFlow()


    init {
        viewModelScope.launch {
            val count = schedulerRepo.getPomodoroCount()
            val startTime = schedulerRepo.getStartTime()

            startTime?.let {
                _startTime.emit("${it.hours}:${it.minutes}")

            }

            _pomodoroTomato.emit(count)
            schedulerRepo.deleteRunningSchedule()
        }
    }
}