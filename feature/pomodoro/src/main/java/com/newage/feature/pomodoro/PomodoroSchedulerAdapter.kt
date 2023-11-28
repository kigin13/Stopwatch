package com.newage.feature.pomodoro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timers.stopwatch.core.common.android.adapter.BaseViewHolder
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity
import com.timers.stopwatch.feature.pomodoro.databinding.PromodoroSchedulerItemViewBinding

class PomodoroSchedulerAdapter : RecyclerView.Adapter<BaseViewHolder<PomodoroScheduleEntity>>() {

    private var schedulerList: List<PomodoroScheduleEntity> = emptyList()
    private var schedulerCallBack: ((SchedulerItemCallback) -> Unit)? = null

    fun schedulerCallBack(schedulerCallBack: ((SchedulerItemCallback) -> Unit)) {
        this.schedulerCallBack = schedulerCallBack
    }
    fun setSchedulerList(schedulerList: List<PomodoroScheduleEntity>) {
        if (this.schedulerList.isEmpty()) {
            this.schedulerList = schedulerList
            notifyItemRangeInserted(0, this.schedulerList.size)
        } else {
            this.schedulerList = schedulerList
            notifyItemRangeChanged(0, this.schedulerList.size)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PomodoroScheduleEntity> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PromodoroSchedulerItemViewBinding
            .inflate(inflater, parent, false)

        return PomodoroViewHolder(binding, schedulerCallBack)
    }

    override fun getItemCount(): Int = schedulerList.size

    override fun onBindViewHolder(holder: BaseViewHolder<PomodoroScheduleEntity>, position: Int) {
        holder.bind(schedulerList[position])
    }
}