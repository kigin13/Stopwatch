package com.newage.feature.pomodoro.presentation.pomodoroResult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.timers.stopwatch.core.common.android.StopwatchFragment
import com.timers.stopwatch.feature.pomodoro.databinding.FragmentPomodoroResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PomodoroResultFragment :
    StopwatchFragment<FragmentPomodoroResultBinding, PomodoroResultViewModel>(
        FragmentPomodoroResultBinding::inflate
    ) {

    override val viewModel: PomodoroResultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}