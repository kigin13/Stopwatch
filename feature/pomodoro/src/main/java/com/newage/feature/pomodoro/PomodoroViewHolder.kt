package com.newage.feature.pomodoro

import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.newage.feature.pomodoro.useCase.TimeManipulationUseCase
import com.timers.stopwatch.core.common.android.R.color
import com.timers.stopwatch.core.common.android.adapter.BaseViewHolder
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
import com.timers.stopwatch.feature.pomodoro.databinding.PromodoroSchedulerItemViewBinding
import java.text.DecimalFormat

class PomodoroViewHolder(
    private val schedulerBinding: PromodoroSchedulerItemViewBinding,
    private val schedulerCallBack: ((SchedulerItemCallback) -> Unit)?
) : BaseViewHolder<PomodoroScheduleEntity>(schedulerBinding.root) {

    override val binding: ViewBinding
        get() = this.schedulerBinding

    override fun bind(model: PomodoroScheduleEntity) {
        val isPomodoro = model.isPomodoro
        schedulerBinding.apply {

            counterContainer.visibility = isPomodoro.getVisibility()
            timeContainer.visibility = (!isPomodoro).getVisibility()

            pomodoroTxt.setText("${model.longBreakAfter}")

            promodoroTitleTxt.text = model.title
            bindTime(Pair(model.hours, model.minutes))

            btnPlus.setOnClickListener {
                handlePlusBtnClicked(isPomodoro)
            }

            btnMinus.setOnClickListener {
                handlePlusBtnClicked(isPomodoro, false)
            }

            hoursView.apply {
                setOnEditorActionListener(editCompleteAction)
                onFocusChangeListener = editFocusChange
            }

            minutesView.apply {
                setOnEditorActionListener(editCompleteAction)
                onFocusChangeListener = editFocusChange
            }

            pomodoroTxt.apply {
                setOnEditorActionListener(editCompleteAction)
                onFocusChangeListener = editFocusChange
            }
        }
    }

    private val editFocusChange = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            Log.d("adapterPosition", layoutPosition.toString())
            schedulerCallBack?.let {
                Log.d("schedulerCallBack", "OnFocusChanged")
                it(SchedulerItemCallback.OnFocusChanged(adapterPosition))
            }
            focusedViewChanged(v)
        }
    }

    fun focusedViewChanged(v: View?) {
        schedulerBinding.apply {
            hoursContainer.strokeWidth = if (v?.id == hoursView.id) 3 else 0
            minutesContainer.strokeWidth = if (v?.id == minutesView.id) 3 else 0
            pomodoroCounter.strokeWidth = if (v?.id == pomodoroTxt.id) 3 else 0
        }

        schedulerBinding.apply {
            labelHour.setTextColor(getColor(v?.id == hoursView.id))
            labelMinute.setTextColor(getColor(v?.id == minutesView.id))
            labelPomodoro.setTextColor(getColor(v?.id == pomodoroTxt.id))
        }

        replaceEmptyText()
    }

    private fun replaceEmptyText() {
        schedulerBinding.apply {
            hoursView.replaceIfEmpty("00")
            minutesView.replaceIfEmpty("00")
            pomodoroTxt.replaceIfEmpty("00")
        }
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


    private fun handlePlusBtnClicked(isPomodoro: Boolean, isPlusBtn: Boolean = true) {
        removeFocus()
        when {
            isPomodoro -> calculatePomodoroCount(isPlusBtn)
            isPlusBtn -> calculateIncreasedTime()
            else -> calculateDecreasedTime()
        }
    }

    private fun calculatePomodoroCount(isPlusBtn: Boolean) {
        var pomodoroCount = schedulerBinding.pomodoroTxt.text.toString().toInt()

        if (pomodoroCount <= 1 && !isPlusBtn) return

        if (isPlusBtn) pomodoroCount++ else pomodoroCount--

        schedulerBinding.pomodoroTxt.setText("$pomodoroCount")

        schedulerCallBack?.let {
//            it(SchedulerItemCallback.OnFocusChanged(adapterPosition))
            it(SchedulerItemCallback.OnPomodoroCountChange(adapterPosition, pomodoroCount))
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

        val hours = DecimalFormat("00").format(newTime.first)
        val minutes = DecimalFormat("00").format(newTime.second)

        schedulerBinding.apply {
            hoursView.setText(hours)
            minutesView.setText(minutes)
        }

        schedulerCallBack?.let {
//            it(SchedulerItemCallback.OnFocusChanged(adapterPosition))
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

private fun EditText.replaceIfEmpty(defaultText: String) {
    if (this.text.isNullOrEmpty()) {
        this.setText(defaultText)
    }
}
