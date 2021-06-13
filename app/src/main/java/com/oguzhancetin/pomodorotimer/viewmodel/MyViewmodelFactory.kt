package com.oguzhancetin.pomodorotimer.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
class MyViewmodelFactory(val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FragmentStatusViewModel::class.java)){
            return FragmentStatusViewModel(application) as T
        }
        if(modelClass.isAssignableFrom(FragmentMainViewmodel::class.java)){
            return FragmentMainViewmodel(application) as T
        }
        if(modelClass.isAssignableFrom(FragmentSettingViewmodel::class.java)){
            return FragmentSettingViewmodel(application) as T
        }
        throw IllegalArgumentException("illegal argument")
    }
}*/
