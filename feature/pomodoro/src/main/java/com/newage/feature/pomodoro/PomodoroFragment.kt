package com.newage.feature.pomodoro

import androidx.fragment.app.viewModels
import com.timers.stopwatch.core.common.android.StopwatchFragment
import com.timers.stopwatch.feature.pomodoro.databinding.FragmentPomodoroBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 01.11.2023.
 */
@AndroidEntryPoint
class PomodoroFragment : StopwatchFragment<FragmentPomodoroBinding, PomodoroViewModel>(
    FragmentPomodoroBinding::inflate,
) {
    override val viewModel: PomodoroViewModel by viewModels<PomodoroViewModel>()
}
