package com.newage.feature.pomodoro

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.newage.feature.pomodoro.model.PomodoroScheduler
import com.newage.feature.pomodoro.model.Time
import com.newage.feature.pomodoro.model.TimerIndicatorModel
import com.newage.feature.pomodoro.useCase.CountDownUseCase
import com.newage.feature.pomodoro.useCase.TimerIndicatorUseCase
import com.timers.stopwatch.core.common.android.StopwatchViewModel
import com.timers.stopwatch.core.data.repository.PomodoroRepository
import com.timers.stopwatch.core.domain.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PomodoroRoundsViewModel @Inject constructor(
    private val repo: PomodoroRepository,
    private val timerUseCase: TimerIndicatorUseCase,
    private val dispatchers: DispatchersProvider,
) : StopwatchViewModel() {

    private val _updatePomodoroProgress = MutableStateFlow(TimerIndicatorModel())
    val updatePomodoroProgress = _updatePomodoroProgress.asStateFlow()

    private val _schedulers = MutableStateFlow(mutableListOf<PomodoroScheduler>())

    private val _currentSchedule = MutableStateFlow(-1)

    val currentSchedulers = _currentSchedule.map {
        when (it == -1) {
            true -> null
            false -> _schedulers.value[it]
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    val countDown = MutableStateFlow(5)

    init {
        startPomodoroCountDown()
        getScheduler()
    }

    private fun getScheduler() {
        viewModelScope.launch(dispatchers.io()) {
            val pomodoroSchedule = repo.getPomodoroSchedules()

            val pomodoroSchedules = emptyList<PomodoroScheduler>().toMutableList()
            pomodoroSchedule.let {

                (0..it[it.lastIndex].longBreakAfter).forEach { index ->
                    pomodoroSchedules += PomodoroScheduler(
                        round = index + 1,
                        title = "Focus",
                        duration = Time(
                            hours = it[0].hours,
                            minutes = it[0].minutes
                        ),
                        isCompleted = false,
                        startTime = Time()
                    )

                    pomodoroSchedules += PomodoroScheduler(
                        round = index + 1,
                        title = "Short Break",
                        duration = Time(
                            hours = it[1].hours,
                            minutes = it[1].minutes
                        ),
                        isCompleted = false,
                        startTime = Time()
                    )
                }

                pomodoroSchedules += PomodoroScheduler(
                    round = 1,
                    title = "Long Break",
                    duration = Time(
                        hours = it[0].hours,
                        minutes = it[0].minutes
                    ),
                    isCompleted = false,
                    startTime = Time()
                )

            }
            _schedulers.emit(pomodoroSchedules)
        }
    }

    private fun startPomodoroCountDown() {
        CountDownUseCase().invoke().onEach {
            countDown.emit(it)
            if (it == 0) {
                initiatePomodoroTimer()
            }
        }.launchIn(viewModelScope)
    }

    private fun initiatePomodoroTimer() {
        val index = _currentSchedule.value + 1

        _schedulers.value[index].let {
            _currentSchedule.value = index
            countdownTimer(calculateSeconds(it.duration)).start()
        }
    }

    private fun calculateSeconds(currentTime: Time): Long =
        (((currentTime.hours * 60) + currentTime.minutes) * 60).toLong()

    private fun countdownTimer(timeSeconds: Long) =
        object : CountDownTimer(timeSeconds * 1000, 1000) {
            override fun onFinish() {
                val index = _currentSchedule.value
                _schedulers.value[index] = _schedulers.value[index].copy(
                    isCompleted = true
                )
                initiatePomodoroTimer()
            }

            override fun onTick(remainingTimeMillis: Long) {
                val remainingTimeSeconds = (remainingTimeMillis / 1000).toInt()
                val percentagePassed = (remainingTimeSeconds.toDouble() / timeSeconds) * 100
                val roundedPercentage = percentagePassed.toFloat()

                val time = timerUseCase.calculateCurrentTime(remainingTimeSeconds)

                viewModelScope.launch {
                    val model = timerUseCase.currentTimeModel(roundedPercentage, time)
                    _updatePomodoroProgress.emit(model)
                }
            }
        }
}