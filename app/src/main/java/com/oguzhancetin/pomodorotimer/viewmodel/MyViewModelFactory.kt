@file:Suppress("UNCHECKED_CAST")

package com.oguzhancetin.pomodorotimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oguzhancetin.pomodorotimer.database.PomodoroRepository
import java.lang.IllegalArgumentException


class MyViewModelFactory(val repository: PomodoroRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FragmentStatusViewModel::class.java)){
            return FragmentStatusViewModel(repository) as T
        }
        if(modelClass.isAssignableFrom(FragmentMainViewmodel::class.java)){
            return FragmentMainViewmodel(repository) as T
        }
        if(modelClass.isAssignableFrom(FragmentSettingViewmodel::class.java)){
            return FragmentSettingViewmodel(repository) as T
        }
        throw IllegalArgumentException("illegal argument")
    }
}
