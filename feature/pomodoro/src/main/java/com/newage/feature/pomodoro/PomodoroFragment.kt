package com.newage.feature.pomodoro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.timers.stopwatch.core.common.android.StopwatchFragment
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import com.timers.stopwatch.feature.pomodoro.databinding.FragmentPomodoroBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 01.11.2023.
 */
@AndroidEntryPoint
class PomodoroFragment : StopwatchFragment<FragmentPomodoroBinding, PomodoroViewModel>(
    FragmentPomodoroBinding::inflate,
) {
    override val viewModel: PomodoroViewModel by viewModels()
    private var pomodoroSchedulerAdapter = PomodoroSchedulerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getScheduler()

        binding.promodoroSetupRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = pomodoroSchedulerAdapter
        }

        viewModel.schedulers.observe(viewLifecycleOwner) {
            if (it.first) {
                pomodoroSchedulerAdapter.setSchedulerList(it.second)
            }
        }

        handlingClickEvents()
    }

    private fun removeFocuses(focusIndex: Int) {
        if (viewModel.schedulers.value?.second?.isEmpty() == true) return

        val oldFocus = viewModel.oldFocus.value
        if (oldFocus == -1) {
            viewModel.oldFocus.value = focusIndex
            return
        }

        viewModel.oldFocus.value = focusIndex

        val holder = binding.promodoroSetupRecycler
            .findViewHolderForAdapterPosition(oldFocus)

        if (holder is PomodoroViewHolder) {
            holder.focusedViewChanged(null)
        }
    }

    private fun handlingClickEvents() {
        binding.buttonContainer.apply {
            stopContainer.visibility = View.GONE

            btnPlayPause.setOnClickListener {

                handleNavigationCommands(
                    NavigationCommand.To(
                        PomodoroFragmentDirections.actionPomodoroFragmentToPomodoroRoundFragment()
                    )
                )
            }

            btnReset.setOnClickListener {
                viewModel.resetPomodoroTimer()
            }
        }

        pomodoroSchedulerAdapter.schedulerCallBack {
            when (it) {
                is SchedulerItemCallback.OnPomodoroCountChange -> viewModel::pomodoroCountChange
                is SchedulerItemCallback.OnTimeChange -> viewModel.setNewTime(it.position, it.newTime)
                is SchedulerItemCallback.OnFocusChanged -> removeFocuses(it.position)
            }
        }
    }
}