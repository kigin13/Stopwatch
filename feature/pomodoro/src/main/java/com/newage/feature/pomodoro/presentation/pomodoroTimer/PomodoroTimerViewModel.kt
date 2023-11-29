package com.newage.feature.pomodoro.presentation.pomodoroTimer

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.newage.feature.pomodoro.model.TimerIndicatorModel
import com.timers.stopwatch.core.domain.usecase.featurePomodoro.CountDownUseCase
import com.timers.stopwatch.core.common.android.StopwatchViewModel
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import com.timers.stopwatch.core.model.PomodoroEnum
import com.timers.stopwatch.core.model.PomodoroRunningScheduler
import com.timers.stopwatch.core.model.PomodoroStatus
import com.timers.stopwatch.core.model.Time
import com.timers.stopwatch.core.domain.repository.PomodoroRepository
import com.timers.stopwatch.core.domain.repository.RunningSchedulerRepo
import com.timers.stopwatch.core.domain.DispatchersProvider
import com.timers.stopwatch.core.model.PomodoroScheduleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PomodoroTimerViewModel @Inject constructor(
    private val repo: PomodoroRepository,
    private val schedulerRepo: RunningSchedulerRepo,
    private val dispatchers: DispatchersProvider,
) : StopwatchViewModel() {

    private val _updatePomodoroProgress = MutableStateFlow(TimerIndicatorModel())
    val updatePomodoroProgress = _updatePomodoroProgress.asStateFlow()

    private val _schedulers = MutableStateFlow((mutableListOf<PomodoroScheduleModel>()))

    private val _currentSchedule = MutableStateFlow(-1)

    private val getCurrentTick = MutableStateFlow(Time())

    private val isPlaying = MutableStateFlow(true)

    private lateinit var countDownTimer: CountDownTimer

    private var countDownFlow: Job? = null

    val currentSchedulers = MutableStateFlow<PomodoroRunningScheduler?>(null)

    val pomodoroTomato = _currentSchedule.map {
        schedulerRepo.getPomodoroCount()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val countDown = MutableStateFlow(5)

    init {
        getCurrentScheduler()
        getScheduler()
    }

    private fun getScheduler() {
        viewModelScope.launch(dispatchers.io()) {
            val pomodoroSchedule = repo.getPomodoroSchedules()
            _schedulers.value = pomodoroSchedule.toMutableList()
        }
    }

    private fun playNextSchedule() {
        _currentSchedule.value += 1
        getCurrentScheduler()
    }

    private fun playPreviousSchedule() {
        if (_currentSchedule.value > 0) {
            _currentSchedule.value -= 1
        }
        getCurrentScheduler()
    }

    private fun getCurrentScheduler() {
        val currentPomodoro = _currentSchedule.value
        if (currentPomodoro == -1) {
            startPomodoroCountDown()
            return
        }

        val pomodoroRounds = _schedulers.value.last().longBreakAfter
        val perPomodoroCycle = (pomodoroRounds * 2) + 1

        val round = (currentPomodoro / perPomodoroCycle) + 1
        val currentCycle = (currentPomodoro + 1) % perPomodoroCycle
        val pomodoro = ((round - 1) * pomodoroRounds) + ((currentCycle + 1) / 2)

        when (currentCycle) {
            0 -> runLongBreak(round, pomodoro)
            else -> when (currentCycle % 2 == 0) {
                true -> runShortBreak(round, pomodoro)
                false -> runFocusMode(round, pomodoro)
            }
        }
    }

    private fun runShortBreak(round: Int, pomodoro: Int) {
        currentSchedulers.value = PomodoroRunningScheduler(
            pomodoro = pomodoro,
            round = round,
            title = PomodoroEnum.SHORT_BREAK,
            duration = Time(
                hours = _schedulers.value[1].hours,
                minutes = _schedulers.value[1].minutes
            ),
            startTime = getCurrentTime()
        )
        saveCurrentScheduleAndStartTimer()
    }

    private fun runFocusMode(round: Int, pomodoro: Int) {
        currentSchedulers.value = PomodoroRunningScheduler(
            pomodoro = pomodoro,
            round = round,
            title = PomodoroEnum.FOCUS,
            duration = Time(
                hours = _schedulers.value[0].hours,
                minutes = _schedulers.value[0].minutes
            ),
            startTime = getCurrentTime()
        )
        saveCurrentScheduleAndStartTimer()
    }

    private fun runLongBreak(round: Int, pomodoro: Int) {
        currentSchedulers.value = PomodoroRunningScheduler(
            pomodoro = pomodoro,
            round = round,
            title = PomodoroEnum.LONG_BREAK,
            duration = Time(
                hours = _schedulers.value[0].hours,
                minutes = _schedulers.value[0].minutes
            ),
            startTime = getCurrentTime()
        )
        saveCurrentScheduleAndStartTimer()
    }

    private fun saveCurrentScheduleAndStartTimer() {
        saveCurrentSchedule()
        currentSchedulers.value?.let {
            countdownTimer(it.duration.getSeconds())
            countDownTimer.start()
        }
    }

    private fun startPomodoroCountDown() {
        countDownFlow = CountDownUseCase().invoke().onEach {
            countDown.emit(it)
            if (it == 0) {
                playNextSchedule()
            }
        }.launchIn(viewModelScope)
    }

    private fun countdownTimer(timeSeconds: Long) {
        countDownTimer = object : CountDownTimer(timeSeconds * 1000, 1000) {
            override fun onFinish() {
                handlePomodoroCompleted()
            }

            override fun onTick(remainingTimeMillis: Long) {
                val remainingTimeSeconds = (remainingTimeMillis / 1000).toInt()
                val percentagePassed =
                    (remainingTimeSeconds.toDouble() / currentSchedulers.value?.duration?.getSeconds()!!) * 100
                val roundedPercentage = percentagePassed.toFloat()

                val time = calculateCurrentTime(remainingTimeSeconds)

                viewModelScope.launch {
                    val model = currentTimeModel(roundedPercentage, time)
                    _updatePomodoroProgress.emit(model)
                }
            }
        }
    }

    private fun handlePomodoroCompleted() {
        updateCurrentPomodoro(PomodoroStatus.COMPLETED)
        playNextSchedule()
    }

    private fun saveCurrentSchedule() {
        viewModelScope.launch {
            currentSchedulers.value?.let {
                val id = schedulerRepo.insertRunningSchedule(it)
                currentSchedulers.value = currentSchedulers.value?.copy(id = id.toInt())
            }
        }
    }

    private fun updateCurrentPomodoro(status: PomodoroStatus) {
        viewModelScope.launch {
            currentSchedulers.value?.let {
                schedulerRepo.updateRunningSchedule(it.id!!, status.name)
            }
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
        isPlaying.value = true
        countDownTimer.cancel()
        updateCurrentPomodoro(PomodoroStatus.CANCELED)
        playNextSchedule()
    }

    fun handleBackwardBtnClick() {
        isPlaying.value = true
        countDownTimer.cancel()
        playPreviousSchedule()
    }

    fun handleResetBtnClick() {
        safeCancelCountDown()
        _currentSchedule.value = -1
        getCurrentScheduler()
    }

    private fun getCurrentTime(): Time {
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        return Time(hours, minutes, seconds)
    }

    fun handleBtnPlayPauseClick() {
        if (isPlaying.value) {
            getCurrentTick.value =
                _updatePomodoroProgress.value.let { Time(it.hours, it.minutes, it.seconds) }

            countDownTimer.cancel()
        } else {
            countdownTimer(getCurrentTick.value.getSeconds())
            countDownTimer.start()
        }
        isPlaying.value = !(isPlaying.value)
    }

    fun handleFinishBtnClick() {
        safeCancelCountDown()
        pomodoroScheduleCompleted()
    }

    private fun safeCancelCountDown() {
        countDownFlow?.cancel()
        try {
            countDownTimer.cancel()
        } catch (_: Exception) {}
    }

    fun calculateCurrentTime(remainingTime: Int): Triple<Int, Int, Int> {
        val hours = remainingTime / 3600
        val remainingSecondsAfterHours = remainingTime % 3600
        val minutes = remainingSecondsAfterHours / 60
        val seconds = remainingSecondsAfterHours % 60
        return Triple(hours, minutes, seconds)
    }

    fun currentTimeModel(percentage: Float, time: Triple<Int, Int, Int>): TimerIndicatorModel {
        return TimerIndicatorModel(
            hours = time.first,
            minutes = time.second,
            seconds = time.third,
            percentage = percentage
        )
    }
}