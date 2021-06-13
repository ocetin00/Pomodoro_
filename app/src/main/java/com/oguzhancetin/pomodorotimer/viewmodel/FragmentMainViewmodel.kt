package com.oguzhancetin.pomodorotimer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.oguzhancetin.pomodorotimer.database.PomodoRepository
import com.oguzhancetin.pomodorotimer.database.Pomodoro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMainViewmodel(application: Application) : AndroidViewModel(application) {
    val mRepository = PomodoRepository(application.baseContext)

    lateinit var allPomodoro: LiveData<List<Pomodoro>>

    init {
        allPomodoro = mRepository.allPomodoro
    }

    fun insertPomodoro(pomodoro: Pomodoro) {
        Log.e("insert", "insert")
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.insertPomodoro(pomodoro)
        }

    }

    fun deleteAlldata() {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.deleteAllPomodoro()
        }
    }


}