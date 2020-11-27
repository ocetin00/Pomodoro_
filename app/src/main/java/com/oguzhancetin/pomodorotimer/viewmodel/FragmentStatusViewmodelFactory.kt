package com.oguzhancetin.pomodorotimer.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FragmentStatusViewmodelFactory(val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FragmentStatusViewModel::class.java)){
            return FragmentStatusViewModel(application) as T
        }
        throw IllegalArgumentException("illegal argument")
    }
}