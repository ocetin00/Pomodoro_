package com.oguzhancetin.pomodorotimer.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.oguzhancetin.pomodorotimer.util.leftTime


class TimerBroadcast : BroadcastReceiver() {


    var state = false
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action


        if(intent != null && action != null && action.equals("com.oguzhancetin.pomodorotimer.SEND_TIME")){
            Log.e("lefts2", intent.getLongExtra("left",0).toString())

                val left = intent.getLongExtra("left",0)

                    leftTime.value = left


        }

    }
}