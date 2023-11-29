package com.newage.feature.pomodoro

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.timers.stopwatch.core.common.android.R.color
import com.timers.stopwatch.core.common.android.adapter.BaseViewHolder
import com.timers.stopwatch.core.domain.usecase.featurePomodoro.PomodoroCountChangeUseCase
import com.timers.stopwatch.core.domain.usecase.featurePomodoro.TimeManipulationUseCase
import com.timers.stopwatch.core.model.PomodoroScheduleModel
import com.timers.stopwatch.feature.pomodoro.databinding.PromodoroSchedulerItemViewBinding

class PomodoroViewHolder(
    private val schedulerBinding: PromodoroSchedulerItemViewBinding,
    private val schedulerCallBack: ((SchedulerItemCallback) -> Unit)?
) : BaseViewHolder<PomodoroScheduleModel>(schedulerBinding.root) {

    override val binding: ViewBinding
        get() = this.schedulerBinding

    override fun bind(model: PomodoroScheduleModel) {
        updateUI(model)
        applyListeners(model)
    }

    private fun updateUI(model: PomodoroScheduleModel) {
        schedulerBinding.apply {
            counterContainer.visibility = model.isPomodoro.getVisibility()
            timeContainer.visibility = (!model.isPomodoro).getVisibility()

            pomodoroTxt.setText("${model.longBreakAfter}")

            promodoroTitleTxt.text = model.title
            bindTime(Pair(model.hours, model.minutes))
        }
    }

    private fun applyListeners(model: PomodoroScheduleModel) {
        schedulerBinding.apply {
            btnPlus.setOnClickListener {
                handleBtnClicked(model.isPomodoro)
            }
            btnMinus.setOnClickListener {
                handleBtnClicked(model.isPomodoro, false)
            }
            hoursView.applyFocus()

            minutesView.applyFocus()

            pomodoroTxt.applyFocus()
        }
    }


    private fun EditText.applyFocus() {
        this.setOnEditorActionListener(editCompleteAction)
        onFocusChangeListener = editFocusChange
    }

    private val editFocusChange = View.OnFocusChangeListener { view, hasFocus ->
        if (hasFocus) {
            schedulerCallBack?.let {
                it(SchedulerItemCallback.OnFocusChanged(adapterPosition))
            }
            focusedViewChanged(view)
        }
    }

    fun focusedViewChanged(view: View?) =
        schedulerBinding.apply {
            hoursContainer.strokeWidth = if (view?.id == hoursView.id) 3 else 0
            minutesContainer.strokeWidth = if (view?.id == minutesView.id) 3 else 0
            pomodoroCounter.strokeWidth = if (view?.id == pomodoroTxt.id) 3 else 0

            labelHour.setTextColor(getColor(view?.id == hoursView.id))
            labelMinute.setTextColor(getColor(view?.id == minutesView.id))
            labelPomodoro.setTextColor(getColor(view?.id == pomodoroTxt.id))
            replaceEmptyText()
        }


    private fun replaceEmptyText() =
        schedulerBinding.apply {
            hoursView.replaceIfEmpty("00")
            minutesView.replaceIfEmpty("00")
            pomodoroTxt.replaceIfEmpty("00")
        }


    private fun getColor(isSelected: Boolean): Int {
        val context = schedulerBinding.root.context
        val colorGray = ContextCompat.getColor(context, color.boulde)
        val colorBlue = ContextCompat.getColor(context, color.dodger_blue_2)
        return if (isSelected) colorBlue else colorGray
    }

    private val editCompleteAction = TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            removeFocus()
        }
        bindTime(
            Pair(
                schedulerBinding.hoursView.text.toString().toInt(),
                schedulerBinding.minutesView.text.toString().toInt()
            )
        )
        false
    }


    private fun handleBtnClicked(isPomodoro: Boolean, isPlusBtn: Boolean = true) {
        removeFocus()
        when {
            isPomodoro -> calculatePomodoroCount(isPlusBtn)
            isPlusBtn -> calculateIncreasedTime()
            else -> calculateDecreasedTime()
        }
    }

    private fun calculatePomodoroCount(isPlusBtn: Boolean) {

        schedulerBinding.apply {
            val newCount = PomodoroCountChangeUseCase.getNewCount(
                pomodoroTxt.text.toString().toInt(),
                isPlusBtn
            )

            pomodoroTxt.setText("$newCount")

            schedulerCallBack?.let {
                it(SchedulerItemCallback.OnPomodoroCountChange(adapterPosition, newCount))
            }
        }
    }

    private fun calculateDecreasedTime() {
        val newTime = TimeManipulationUseCase.decreaseTime(
            schedulerBinding.hoursView.text.toString().toInt(),
            schedulerBinding.minutesView.text.toString().toInt()
        )
        bindTime(newTime)
    }

    private fun calculateIncreasedTime() {
        val newTime = TimeManipulationUseCase.increaseTime(
            schedulerBinding.hoursView.text.toString().toInt(),
            schedulerBinding.minutesView.text.toString().toInt()
        )
        bindTime(newTime)
    }

    private fun bindTime(newTime: Pair<Int, Int>) {
        if (newTime.first < 0) return

        val hours = newTime.first.toTimeString()
        val minutes = newTime.second.toTimeString()

        schedulerBinding.apply {
            hoursView.setText(hours)
            minutesView.setText(minutes)
        }

        schedulerCallBack?.let {
            it(SchedulerItemCallback.OnTimeChange(adapterPosition, newTime))
        }
    }

    private fun removeFocus() {
        schedulerBinding.apply {
            hoursView.clearFocus()
            minutesView.clearFocus()
            pomodoroTxt.clearFocus()
        }
        focusedViewChanged(null)
    }
}