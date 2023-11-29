package com.timers.stopwatch.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.timers.stopwatch.core.database.model.PomodoroScheduleEntity

@Dao
interface PomodoroScheduleDao {

    @Insert
    fun insertPromodoroSchedule(scheduler: List<PomodoroScheduleEntity>)

    @Query("SELECT * FROM promodoro_schedule")
    suspend fun getPromodoroSchedule(): List<PomodoroScheduleEntity>

    @Update
    suspend fun updateScheduler(model: PomodoroScheduleEntity)

    @Query("UPDATE promodoro_schedule SET hours=" + "0" + ", minutes=" + "0" + ", longBreakAfter=" + "1")
    suspend fun resetSchedulers()
}