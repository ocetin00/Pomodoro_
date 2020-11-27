package com.oguzhancetin.pomodorotimer.background

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.oguzhancetin.pomodorotimer.MainActivity
import com.oguzhancetin.pomodorotimer.R
import com.oguzhancetin.pomodorotimer.util.Times
import com.oguzhancetin.pomodorotimer.util.TimesSharedPreferences

class MyService : Service() {


    private lateinit var pendingIntent:PendingIntent
    private lateinit var notification:Notification
    private var timer:CountDownTimer? = null





    override fun onCreate() {
        super.onCreate()



    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //time type to start timer
        val timeType:Times = intent?.getSerializableExtra("timeType") as Times
        val time = TimesSharedPreferences.getSharred(this.applicationContext)?.getLong(timeType.name,timeType.time)
        Log.e("timefrompref",(time!!/2000).toString()+"time type ${timeType.name}")

        timer?.let {
            it.cancel()
        }
        createTimer(time)

        timer?.start()

        var intentnotification = Intent(this, MainActivity::class.java)
        intentnotification.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)


        pendingIntent = PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(),
            intentnotification,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification = NotificationCompat.Builder(this, "channel2")
            .setContentTitle("pomodoro")
            .setContentText("textofContent")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setTicker("tiker")
            .setContentIntent(pendingIntent)
            .build()

        createNotificationChannel()
        startForeground(3, notification)
        Log.e("service", "onstartCommand")


        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
    private fun createTimer(time: Long){
       timer =  object : CountDownTimer(time, 1000){
            override fun onTick(millisUntilFinished: Long) {
                /*Toast.makeText(
                    applicationContext,
                    (millisUntilFinished / 1000).toString(),
                    Toast.LENGTH_SHORT
                ).show()*/

                //save pomodoro to show in graph


                //send left time
                Intent("com.oguzhancetin.pomodorotimer.SEND_TIME").apply {
                    putExtra("left", millisUntilFinished)
                    Log.e("kalan", (millisUntilFinished / 2000).toString())
                    sendBroadcast(this)
                }
            }

            override fun onFinish() {
                Log.e("kaln", "bitti")
                Intent("com.oguzhancetin.pomodorotimer.SEND_TIME").apply {
                    putExtra("left",0)
                    sendBroadcast(this)
                }
                stopSelf()
            }
        }


    }

    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "pomodoroChannel2"
            val descriptionText ="pomodoro2"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("channel2", name, importance).apply {
                description = descriptionText
            }

            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
        else{

        }

    }
}