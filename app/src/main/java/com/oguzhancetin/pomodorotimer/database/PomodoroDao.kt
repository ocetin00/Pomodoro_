package com.oguzhancetin.pomodorotimer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.time.DayOfWeek

@Dao
interface PomodoroDao {

    @Query("SELECT * FROM Pomodoro WHERE finished_date > (1606728525072-((86400000)*:dayOfWeek))")
    fun getAllPomodoro(dayOfWeek: Int): LiveData<List<Pomodoro>>

    @Insert
    suspend fun insert(pomodoro: Pomodoro)

    @Query("DELETE FROM Pomodoro")
    suspend fun deleteAllPomodoro()
}