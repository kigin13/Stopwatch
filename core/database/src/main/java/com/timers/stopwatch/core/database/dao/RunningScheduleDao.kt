package com.timers.stopwatch.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.timers.stopwatch.core.database.constants.PomodoroDbConstants.RUNNING_SCHEDULE
import com.timers.stopwatch.core.database.model.RunningScheduleEntity
import com.timers.stopwatch.core.database.model.Time

@Dao
interface RunningScheduleDao {
    @Insert
    suspend fun insertRunningSchedule(scheduler: RunningScheduleEntity): Long

    @Query("SELECT COUNT(*) FROM $RUNNING_SCHEDULE WHERE title= :title  AND status= :status")
    suspend fun getPomodoroCount(
        title: String,
        status: String
    ): Int

    @Query("SELECT startTime FROM $RUNNING_SCHEDULE LIMIT 1")
    suspend fun getStartTime(): Time

    @Query("UPDATE $RUNNING_SCHEDULE SET status = :status WHERE id = :id")
    suspend fun updateRunningSchedule(id: Int, status: String)

    @Query("DELETE FROM $RUNNING_SCHEDULE")
    suspend fun deleteRunningSchedule()
}