package com.timers.stopwatch.core.common.android.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Patrice Mulindi email(mulindipatrice00@gmail.com) on 05.12.2022.
 */
abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract val binding: ViewBinding
    abstract fun bind(model: T)
}
