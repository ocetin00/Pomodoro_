package com.oguzhancetin.pomodorotimer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oguzhancetin.pomodorotimer.database.PomodoRepository
import com.oguzhancetin.pomodorotimer.database.Pomodoro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

class FragmentStatusViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository = PomodoRepository(application.baseContext)

    var allPomodoro: LiveData<List<Pomodoro>>


    init {
        allPomodoro = mRepository.allPomodoro
    }

    fun deleteAlldata() {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.deleteAllPomodoro()
        }
    }


}