package com.newage.feature.pomodoro

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.timers.stopwatch.core.common.android.R
import com.timers.stopwatch.core.common.android.StopwatchFragment
import com.timers.stopwatch.feature.pomodoro.databinding.FragmentPomodoroRoundsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class PomodoroRoundsFragment :
    StopwatchFragment<FragmentPomodoroRoundsBinding, PomodoroRoundsViewModel>(
        FragmentPomodoroRoundsBinding::inflate
    ) {

    override val viewModel: PomodoroRoundsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countDownTxt.visibility = View.VISIBLE

        launchObserver()
        handlingClickEvents()
    }

    private fun handlingClickEvents() {
        binding.forwardButton.setOnClickListener {

        }
    }

    private fun launchObserver() {
        lifecycleScope.launch {
            launch {
                viewModel.countDown.collectLatest {
                    val currentCountDownText = "Starting in\n$it"
                    binding.countDownTxt.text = currentCountDownText

                    if (it == 0) {
                        binding.countDownTxt.visibility = View.GONE
                    }
                }
            }

            launch {
                viewModel.updatePomodoroProgress.collectLatest {
                    Log.d("updatePomodoroProgress", "onViewCreated: $it")
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
                            "Focus" -> R.color.progress_green
                            "Short Break" -> R.color.error_text_color
                            else -> R.color.dodger_blue_2
                        }
                        val color = ContextCompat.getColor(requireContext(), colorRes)
                        val subTitleTxt = "Round ${schedule.round}"

                        binding.apply {
                            timerContainer.visibility = View.VISIBLE
                            title.apply {
                                text = schedule.title
                                setTextColor(color)
                            }
                            subTitle.text = subTitleTxt
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