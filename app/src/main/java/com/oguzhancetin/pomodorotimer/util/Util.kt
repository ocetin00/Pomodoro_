package com.oguzhancetin.pomodorotimer.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData


//get sharedPreferences
class TimesSharedPreferences private constructor(){

    companion object{
       private var sharedPref:SharedPreferences? = null
       fun getSharred(context: Context): SharedPreferences?{
            initializeSharedPref(context)
        return sharedPref
        }
        private fun initializeSharedPref(context: Context){
            if(sharedPref == null){
                sharedPref = context
                    .getSharedPreferences("com.oguzhancetin.pomodorotimer.TIMES",Context.MODE_PRIVATE)
            }


        }
    }
}



