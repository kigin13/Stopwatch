package com.newage.feature.pomodoro.presentation.pomodoroResult

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.timers.stopwatch.core.common.android.StopwatchFragment
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import com.timers.stopwatch.feature.pomodoro.R
import com.timers.stopwatch.feature.pomodoro.databinding.FragmentPomodoroResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PomodoroResultFragment :
    StopwatchFragment<FragmentPomodoroResultBinding, PomodoroResultViewModel>(
        FragmentPomodoroResultBinding::inflate
    ) {

    override val viewModel: PomodoroResultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modifyViews()
        handleClickEvents()

        lifecycleScope.launch {
            launch {
                viewModel.pomodoroTomato.collectLatest {
                    if (it == 0) {
                        binding.apply {
                            resultImage.setImageResource(R.drawable.failed_finish)
                            resultSubTitle.text = getString(R.string.failure_sub_string)
                            resultTitle.text = getString(R.string.failure_title_string)
                        }
                    } else {
                        (1..it).forEach { _ ->
                            val tomato = ImageView(requireContext()).apply {
                                setPadding(10)
                                setImageResource(R.drawable.tomato)
                            }
                            val subtitle = "You did $it pomodoros"
                            binding.apply {
                                resultImage.setImageResource(R.drawable.success_finish)
                                resultTitle.text = getString(R.string.success_title_string)
                                resultSubTitle.text = subtitle
                                pomodoroView.addView(tomato)
                            }
                        }
                    }
                }
            }

            launch {
                viewModel.startTime.collectLatest {
                    binding.txtStartTime.text = it
                }
            }
        }
    }

    private fun handleClickEvents() {
        binding.toolBar.promodoroBackBtn.setOnClickListener {
            handleNavigationCommands(
                NavigationCommand.Back
            )
        }
        binding.buttonContainer.btnReset.setOnClickListener {
            handleNavigationCommands(
                NavigationCommand.To(PomodoroResultFragmentDirections.actionPomodoroResultFragmentToPomodoroFragment())
            )
        }
    }

    private fun modifyViews() {
        binding.buttonContainer.apply {
            playPauseContainer.visibility = View.GONE
            stopContainer.visibility = View.GONE
        }
    }
}