package com.newage.feature.pomodoro.presentation.pomodoroTimer

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.newage.feature.pomodoro.model.PomodoroScheduler
import com.newage.feature.pomodoro.model.PomodoroStatus
import com.newage.feature.pomodoro.model.Time
import com.newage.feature.pomodoro.model.TimerIndicatorModel
import com.newage.feature.pomodoro.useCase.CountDownUseCase
import com.newage.feature.pomodoro.useCase.TimerIndicatorUseCase
import com.timers.stopwatch.core.common.android.StopwatchViewModel
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import com.timers.stopwatch.core.data.repository.PomodoroRepository
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
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
class PomodoroTimerViewModel @Inject constructor(
    private val repo: PomodoroRepository,
    private val timerUseCase: TimerIndicatorUseCase,
    private val dispatchers: DispatchersProvider,
) : StopwatchViewModel() {

    private val _updatePomodoroProgress = MutableStateFlow(TimerIndicatorModel())
    val updatePomodoroProgress = _updatePomodoroProgress.asStateFlow()

    private val _schedulers = MutableStateFlow(mutableListOf<PomodoroScheduler>())

    private val _currentSchedule = MutableStateFlow(-1)

    private lateinit var countDownTimer: CountDownTimer

    val currentSchedulers = _currentSchedule.map {
        when (it == -1) {
            true -> null
            false -> _schedulers.value[it]
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val pomodoroTomato = _currentSchedule.map {
        _schedulers.value.count { schedule ->
            (schedule.status == PomodoroStatus.COMPLETED) && (schedule.title == "Focus")
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val countDown = MutableStateFlow(5)

    init {
        startPomodoroCountDown()
        getScheduler()
    }

    private fun getScheduler() {
        viewModelScope.launch(dispatchers.io()) {
            val pomodoroSchedule = repo.getPomodoroSchedules()

            val pomodoroSchedules = generateScheduler(pomodoroSchedule)

            _schedulers.emit(pomodoroSchedules)
        }
    }

    private fun generateScheduler(schedulerEntry: List<PomodoroScheduleEntity>): MutableList<PomodoroScheduler> {
        val pomodoroSchedules = emptyList<PomodoroScheduler>().toMutableList()

        val pomodoroCount: Int = schedulerEntry[schedulerEntry.lastIndex].longBreakAfter

        (1..pomodoroCount).forEach { index ->
            pomodoroSchedules += PomodoroScheduler(
                round = index,
                title = "Focus",
                duration = Time(
                    hours = schedulerEntry[0].hours,
                    minutes = schedulerEntry[0].minutes
                )
            )

            pomodoroSchedules += PomodoroScheduler(
                round = index,
                title = "Short Break",
                duration = Time(
                    hours = schedulerEntry[1].hours,
                    minutes = schedulerEntry[1].minutes
                )
            )
        }

        pomodoroSchedules += PomodoroScheduler(
            round = 1,
            title = "Long Break",
            duration = Time(
                hours = schedulerEntry[0].hours,
                minutes = schedulerEntry[0].minutes
            )
        )
        return pomodoroSchedules
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

        if (_schedulers.value.size < 1) return
        _schedulers.value[index].let {
            _currentSchedule.value = index
            _schedulers.value[index] = _schedulers.value[index].copy(
                status = PomodoroStatus.RUNNING
            )
            countdownTimer(calculateSeconds(it.duration))
            countDownTimer.start()
        }
    }

    private fun calculateSeconds(currentTime: Time): Long =
        (((currentTime.hours * 60) + currentTime.minutes) * 60).toLong()

    private fun countdownTimer(timeSeconds: Long) {
        countDownTimer = object : CountDownTimer(timeSeconds * 1000, 1000) {
            override fun onFinish() {
                val index = _currentSchedule.value
                _schedulers.value[index] = _schedulers.value[index].copy(
                    status = PomodoroStatus.COMPLETED
                )
                handlePomodoroTimer(index)
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

    private fun handlePomodoroTimer(index: Int) {
        countDownTimer.cancel()

        if (_schedulers.value.lastIndex < index + 1) {
            pomodoroScheduleCompleted()
        } else {
            initiatePomodoroTimer()
        }
    }

    private fun pomodoroScheduleCompleted() {
        navigate(
            NavigationCommand.To(
                PomodoroTimerFragmentDirections.actionPomodoroRoundFragmentToPomodoroResultFragment()
            )
        )
    }

    fun handleForwardBtnClick() {
        val index = _currentSchedule.value
        _schedulers.value[index] = _schedulers.value[index].copy(
            status = PomodoroStatus.CANCELED
        )
        handlePomodoroTimer(index)
    }

    fun handleBackwardBtnClick() {
        _currentSchedule.value -= 2
        handlePomodoroTimer(_currentSchedule.value)
    }
}