package com.oguzhancetin.pomodorotimer.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.oguzhancetin.pomodorotimer.util.leftTime

class TimerBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action?.equals("com.oguzhancetin.pomodorotimer.SEND_TIME")!!){
            Log.e("gelene", intent.getLongExtra("left",0).toString())


                leftTime.value = intent.getLongExtra("left",0)


        }

    }
}