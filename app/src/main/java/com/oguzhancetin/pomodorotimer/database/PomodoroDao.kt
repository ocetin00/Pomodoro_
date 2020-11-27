package com.oguzhancetin.pomodorotimer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PomodoroDao {

    @Query("SELECT * FROM Pomodoro")
    fun getAllPomodoro(): LiveData<List<Pomodoro>>

    @Insert
    suspend fun insert(pomodoro: Pomodoro)

    @Query("DELETE FROM Pomodoro")
    suspend fun deleteAllPomodoro()
}