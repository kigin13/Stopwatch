package com.newage.feature.pomodoro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timers.stopwatch.core.common.android.StopwatchViewModel
import com.timers.stopwatch.core.domain.DispatchersProvider
import com.timers.stopwatch.core.domain.repository.PomodoroRepository
import com.timers.stopwatch.core.model.PomodoroScheduleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 01.11.2023.
 */
@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val repo: PomodoroRepository,
    private val dispatchers: DispatchersProvider,
) : StopwatchViewModel() {

    val oldFocus = MutableStateFlow(-1)

    private val _schedulers = MutableLiveData<Pair<Boolean, List<PomodoroScheduleModel>>>(
        Pair(false, emptyList())
    )
    val schedulers: LiveData<Pair<Boolean, List<PomodoroScheduleModel>>> = _schedulers

    fun getScheduler() {
        viewModelScope.launch(dispatchers.io()) {
            val pomodoroSchedule = repo.getPomodoroSchedules()
            _schedulers.postValue(Pair(true, pomodoroSchedule))
        }
    }

    fun setNewTime(index: Int, newTime: Pair<Int, Int>) {
        val temp = schedulers.value?.second?.toMutableList()
            ?: emptyList<PomodoroScheduleModel>().toMutableList()

        if (index < 0) return

        temp[index] = temp[index].copy(
            hours = newTime.first,
            minutes = newTime.second
        )
        updateSchedule(temp[index])
        _schedulers.postValue(Pair(false, temp))
    }

    fun pomodoroCountChange(index: Int, count: Int) {
        val temp = schedulers.value?.second?.toMutableList()
            ?: emptyList<PomodoroScheduleModel>().toMutableList()

        if (index < 0) return

        temp[index] = temp[index].copy(
            longBreakAfter = count
        )

        updateSchedule(temp[index])

        _schedulers.postValue(Pair(false, temp))
    }

    private fun updateSchedule(updatedSchedule: PomodoroScheduleModel) {
        viewModelScope.launch {
            repo.updateSchedule(updatedSchedule)
        }
    }

    fun resetPomodoroTimer() {
        val temp = _schedulers.value?.second?.map { schedule ->
            schedule.copy(
                hours = 0,
                minutes = 0,
                longBreakAfter = 1
            )
        }

        viewModelScope.launch {
            repo.resetSchedulers()
        }
        temp?.let {
            _schedulers.postValue(Pair(true, it))
        }
    }
}
