package com.newage.feature.pomodoro.presentation.pomodoroTimer

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.newage.feature.pomodoro.Constants
import com.newage.feature.pomodoro.toTimeString
import com.timers.stopwatch.core.common.android.R.color
import com.timers.stopwatch.core.common.android.StopwatchFragment
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import com.timers.stopwatch.core.data.model.PomodoroEnum
import com.timers.stopwatch.feature.pomodoro.R
import com.timers.stopwatch.feature.pomodoro.databinding.FragmentPomodoroTimerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class PomodoroTimerFragment :
    StopwatchFragment<FragmentPomodoroTimerBinding, PomodoroTimerViewModel>(
        FragmentPomodoroTimerBinding::inflate
    ) {

    override val viewModel: PomodoroTimerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countDownTxt.visibility = View.VISIBLE

        modifyViews()
        launchObserver()
        handlingClickEvents()
    }

    private fun modifyViews() {
        binding.apply {
            toolBar.promodoroValueResetBtn.visibility = View.GONE
            buttonContainer.apply {
                playPauseIcon.setImageResource(R.drawable.ic_pause)
                stopFinishIcon.setImageResource(R.drawable.ic_check)
                playPauseText.text = Constants.PAUSE
                stopFinishText.text = Constants.FINISH
            }
        }
    }

    private fun handlingClickEvents() {
        binding.toolBar.promodoroBackBtn.setOnClickListener {
            handleNavigationCommands(
                NavigationCommand.Back
            )
        }
        binding.forwardButton.setOnClickListener {
            viewModel.handleForwardBtnClick()
        }
        binding.backwardButton.setOnClickListener {
            viewModel.handleBackwardBtnClick()
        }
        binding.buttonContainer.apply {
            btnReset.setOnClickListener {
                viewModel.handleResetBtnClick()
            }

            btnPlayPause.setOnClickListener {
                viewModel.handleBtnPlayPauseClick()
            }

            btnStop.setOnClickListener {
                viewModel.handleFinishBtnClick()
            }
        }
    }

    private fun launchObserver() {
        lifecycleScope.launch {

            launch {
                viewModel.pomodoroTomato.collectLatest {
                    binding.pomodoroView.removeAllViews()
                    (1..it).forEach { _ ->
                        val tomato = ImageView(requireContext()).apply {
                            setPadding(10)
                            setImageResource(R.drawable.tomato)
                        }
                        binding.pomodoroView.addView(tomato)
                    }
                }
            }

            launch {
                viewModel.countDown.collectLatest {
                    val currentCountDownText = "Starting in\n$it"
                    binding.countDownTxt.text = currentCountDownText

                    if (it == 0) {
                        binding.countDownTxt.visibility = View.GONE
                    } else {
                        binding.apply {
                            countDownTxt.visibility = View.VISIBLE
                            timerContainer.visibility = View.GONE
                        }
                    }
                }
            }

            launch {
                viewModel.updatePomodoroProgress.collectLatest {
                    if (it.percentage == 0F) {
                        return@collectLatest
                    }
                    updateProgressBar(
                        it.percentage,
                        it.run { "${minutes.toTimeString()}:${seconds.toTimeString()}" })
                }
            }

            launch {
                viewModel.currentSchedulers.collectLatest {
                    it?.let { schedule ->
                        val colorRes = when (schedule.title) {
                            PomodoroEnum.FOCUS -> color.progress_green
                            PomodoroEnum.LONG_BREAK -> color.dodger_blue_2
                            PomodoroEnum.SHORT_BREAK -> color.error_text_color
                        }

                        val color = ContextCompat.getColor(requireContext(), colorRes)
                        val subTitleTxt = "Round ${schedule.round}"
                        val pomodoroTxt = "Pomodoro ${schedule.pomodoro}"

                        binding.apply {
                            timerContainer.visibility = View.VISIBLE
                            title.apply {
                                text = schedule.title.text
                                setTextColor(color)
                            }
                            subTitle.text = subTitleTxt
                            pomodoroTextCount.text = pomodoroTxt
                            pomodoroTextCount.setTextColor(color)
                            progressBarCircle.progressTintList = ColorStateList.valueOf(color)
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBar(progress: Float, time: String) {
        binding.apply {
            progressBarCircle.progress = progress.roundToInt()
            textViewTime.text = time
        }
    }
}