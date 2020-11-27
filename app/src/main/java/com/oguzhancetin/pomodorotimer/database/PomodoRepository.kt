package com.oguzhancetin.pomodorotimer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PomodoRepository(context: Context) {

    lateinit var  allPomodoro:LiveData<List<Pomodoro>>


    private val pomodoroDao = PomodoroDatabase.getDatabase(context).pomodoroDao()

    init {

        allPomodoro = pomodoroDao.getAllPomodoro()
    }

    suspend fun insertPomodoro(pomodoro: Pomodoro){
        pomodoroDao.insert(pomodoro)
    }

    suspend fun deleteAllPomodoro(){
        pomodoroDao.deleteAllPomodoro()
    }

}