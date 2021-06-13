package com.oguzhancetin.pomodorotimer.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class PomodoRepository(context: Context) {

    lateinit var  allPomodoro:LiveData<List<Pomodoro>>


    private val pomodoroDao = PomodoroDatabase.getDatabase(context).pomodoroDao()

    init {
        val numberOfDayOfWeek =  LocalDateTime.ofInstant(java.util.Date(System.currentTimeMillis()).toInstant(),
            ZoneId.systemDefault()).dayOfWeek.value
        val todayMilis = System.currentTimeMillis()
        val aDayMilis = (86400000L)
        val totalMilis = (todayMilis)-((numberOfDayOfWeek-1)*aDayMilis)

        allPomodoro = pomodoroDao.getAllPomodoro(totalMilis)

    }

    suspend fun insertPomodoro(pomodoro: Pomodoro){
        pomodoroDao.insert(pomodoro)
    }

    suspend fun deleteAllPomodoro(){
        pomodoroDao.deleteAllPomodoro()
    }

}