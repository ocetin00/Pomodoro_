package com.oguzhancetin.pomodorotimer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguzhancetin.pomodorotimer.database.PomodoRepository
import com.oguzhancetin.pomodorotimer.database.Pomodoro

class FragmentStatusViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository = PomodoRepository(application.baseContext)

    lateinit var allPomodoro: LiveData<List<Pomodoro>>


    init {
        allPomodoro = mRepository.allPomodoro
    }
}