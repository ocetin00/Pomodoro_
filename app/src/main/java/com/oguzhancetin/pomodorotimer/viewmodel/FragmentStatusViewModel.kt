package com.oguzhancetin.pomodorotimer.viewmodel

import androidx.lifecycle.*
import com.oguzhancetin.pomodorotimer.database.PomodoroRepository
import com.oguzhancetin.pomodorotimer.database.Pomodoro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentStatusViewModel(private val repository: PomodoroRepository) : ViewModel() {


    var allPomodoro: LiveData<List<Pomodoro>> = repository.allPomodoro


    fun deleteAlldata() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPomodoro()
        }
    }


}