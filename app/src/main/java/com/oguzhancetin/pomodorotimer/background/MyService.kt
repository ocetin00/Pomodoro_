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
import androidx.core.app.NotificationManagerCompat
import com.oguzhancetin.pomodorotimer.MainActivity
import com.oguzhancetin.pomodorotimer.R
import com.oguzhancetin.pomodorotimer.util.Times
import com.oguzhancetin.pomodorotimer.util.TimesSharedPreferences
import com.oguzhancetin.pomodorotimer.util.leftTime

class MyService : Service() {


    private lateinit var pendingIntent: PendingIntent
    private lateinit var notification: Notification
    private var timer: CountDownTimer? = null



    override fun onCreate() {
        super.onCreate()

        var intentnotification = Intent(this, MainActivity::class.java)
        intentnotification.flags =
            (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)


        pendingIntent = PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(),
            intentnotification,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //time type to start timer
        val timeType: Times = intent?.getSerializableExtra("timeType") as Times
        //get default time
        val time = TimesSharedPreferences.getSharred(this.applicationContext)
            ?.getLong(timeType.name, timeType.time)
        Log.e("timefrompref", (time!! / 60000).toString() + "time type ${timeType.name}")


        cancelTime(timer)
        timer = null
        if (timer == null) {
            createTimer(time)
        }
        timer?.start()


        notification = NotificationCompat.Builder(this, "channelLow")
            .setContentTitle("Pomodoro Timer")
            .setContentText("Pomodoro Running")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setTicker("tiker")
            .setContentIntent(pendingIntent)
            .build()

        createNotificationChannel()
        startForeground(3, notification)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    private fun createTimer(time: Long) {
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                //save pomodoro to show in graph
                Log.e("has", timer.toString())

                //send left time
                leftTime.postValue(millisUntilFinished)
               /* Intent("com.oguzhancetin.pomodorotimer.SEND_TIME").apply {
                    putExtra("left", millisUntilFinished)
                    Log.e("kalan", (millisUntilFinished).toString())
                    sendBroadcast(this)

                }*/
            }

            override fun onFinish() {
                Log.e("kaln", "bitti")
                leftTime.postValue(0L)
                Intent("com.oguzhancetin.pomodorotimer.SEND_TIME").apply {
                    putExtra("left", "x")
                    sendBroadcast(this)
                }
                stopSelf()
                createNotification(this@MyService)


            }
        }


    }

    //create notification
    fun createNotification(context: Context) {


        var builder: Notification? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var b = NotificationCompat.Builder(context, "channelHigh")
                .setSmallIcon(R.drawable.ic_baseline_timer_24)
                .setContentTitle("Time is over")
                .setContentText("Pomodoro stopped")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                //.setOngoing(true)


            builder = b.build()
        } else {
            var b = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_baseline_timer_24)
                .setContentTitle("Time is over")
                .setContentText("Pomodoro stopped")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
               // .setOngoing(true)
            builder = b.build()



        }
        builder.flags += Notification.FLAG_AUTO_CANCEL
        with(NotificationManagerCompat.from(context)) {

            notify(111, builder)
        }
    }


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelHigh = NotificationChannel("channelHigh", "HighChannel", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "High Channel"
            }
            val channelLow = NotificationChannel("channelLow", "LowChannel", NotificationManager.IMPORTANCE_LOW).apply {
                description = "Low Channel"
            }


            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channelHigh)
            notificationManager.createNotificationChannel(channelLow)


        }
    }

    fun cancelTime(timer: CountDownTimer?) {
        if (timer != null) {
            timer.cancel()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}
