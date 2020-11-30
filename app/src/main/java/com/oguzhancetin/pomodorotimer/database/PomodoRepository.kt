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
        val today = LocalDateTime.now()
        val t2 =  LocalDateTime.ofInstant(java.util.Date(System.currentTimeMillis()).toInstant(), ZoneId.systemDefault())


        allPomodoro = pomodoroDao.getAllPomodoro(t2.dayOfWeek.value)
        Log.e("dayof",t2.dayOfWeek.value.toString())
    }

    suspend fun insertPomodoro(pomodoro: Pomodoro){
        pomodoroDao.insert(pomodoro)
    }

    suspend fun deleteAllPomodoro(){
        pomodoroDao.deleteAllPomodoro()
    }

}