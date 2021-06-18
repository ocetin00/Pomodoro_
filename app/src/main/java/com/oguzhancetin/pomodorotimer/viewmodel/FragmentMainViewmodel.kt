package com.oguzhancetin.pomodorotimer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhancetin.pomodorotimer.database.PomodoroRepository
import com.oguzhancetin.pomodorotimer.database.Pomodoro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMainViewmodel(private val repository: PomodoroRepository) : ViewModel() {


    var allPomodoro  = repository.allPomodoro


    fun insertPomodoro(pomodoro: Pomodoro) {
        Log.e("insert", "insert")
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPomodoro(pomodoro)
        }

    }

    fun deleteAlldata() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPomodoro()
        }
    }


}