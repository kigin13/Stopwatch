package com.timers.stopwatch.core.data.source.impl.converter

import com.timers.stopwatch.core.database.model.TimeEntity
import com.timers.stopwatch.core.model.Time

fun Time.asEntity() =
    TimeEntity(
        this.hours, this.minutes, this.seconds
    )

fun TimeEntity.asModel() =
    Time(
        this.hours, this.minutes, this.seconds
    )