package com.oguzhancetin.pomodorotimer.fragment

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import com.oguzhancetin.pomodorotimer.util.leftTime
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oguzhancetin.pomodorotimer.background.MyService
import com.oguzhancetin.pomodorotimer.database.Pomodoro
import com.oguzhancetin.pomodorotimer.databinding.FragmentMainBinding
import com.oguzhancetin.pomodorotimer.util.Times
import com.oguzhancetin.pomodorotimer.util.TimesSharedPreferences

import com.oguzhancetin.pomodorotimer.viewmodel.FragmentMainViewmodel
import java.util.*


class FragmentMain : Fragment() {
    private var serviceIntent:Intent? = null
    private lateinit var broadcastReceiver: BroadcastReceiver;

    //Display time on the main fragment 00:00
    private lateinit var timeText:TextView

    private lateinit var viewModel:FragmentMainViewmodel

    private lateinit var mCalendar: Calendar
    //check to set graph state
    var graphState = false
    private lateinit var progressCircle:ProgressBar
    private  var progress = 100
    private  var leftGraph = 1L

    private var globalLeft = 10000L
    private var global_time_type = Times.START_TIME
    private var global_time_control = true

    //1sec
    val oneMin = 60000L
    val oneSec = 1000L



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        progressCircle = binding.progress
        progressCircle.progress = progress

        viewModel = ViewModelProvider(this).get(FragmentMainViewmodel::class.java)
        //viewModel.deleteAlldata()
        mCalendar = Calendar.getInstance()

        timeText = binding.textViewTimeLeft

        serviceIntent = Intent(requireActivity(), MyService::class.java)


        //start long break
        binding.buttonLongbreak.setOnClickListener {
            startTimeService(Times.LONG_BREAK)
            graphState = true
        }
        //start short break
        binding.buttonShortBreak.setOnClickListener {

            startTimeService(Times.SHORT_BREAK)
            graphState = true
        }
        //start pomodoro
        binding.buttonStart.setOnClickListener {

            startTimeService(Times.START_TIME)
            graphState = true


        }

        //to write default time before start (25:00)
        val longTime = TimesSharedPreferences
            .getSharred(requireActivity())?.getLong(Times.START_TIME.name,Times.START_TIME.time)
        val longTimeString = printLeftMinutes(longTime)
        timeText.text = longTimeString

       /* viewModel.allPomodoro.observe(viewLifecycleOwner, Observer {
            it.forEach {
                mCalendar.timeInMillis = (it.finished_date_milis)
                Log.e("pomodoro",mCalendar.get(Calendar.DAY_OF_MONTH).toString()+"/"+mCalendar.get(Calendar.MONDAY).toString())
            }
        })*/

        return binding.root
    }

    private fun printLeftMinutes(longTime: Long?) :String {
        var minute = (longTime!! / oneMin).toString()
        var second = ((longTime % oneMin) / oneSec).toString()
        if(minute.length == 1) minute = "0${minute}"
        if(second.length == 1) second = "0${second}"

       return "${minute} : ${second}";
    }


    @SuppressLint("LongLogTag")
    fun startTimeService(time: Times){
        clearService()
        global_time_type = time
        global_time_control = false;

        //var firstTrigger = false


            serviceIntent.also {
            it?.putExtra("timeType", time)
            requireActivity().startService(it)
        }

            if (leftTime.hasObservers()) {
                leftTime.removeObservers(viewLifecycleOwner)
            }

            leftTime.observe(requireActivity(), Observer {
               // if(firstTrigger){

                   // val leftString = (it/oneMin).toString()+" : "+(((it%oneMin)/oneSec)+1).toString()
                    timeText.text = printLeftMinutes(it)
                    //globalLeft = it
                    progress =  (((it.toDouble()/leftGraph.toDouble())*100)).toInt()
                    progressCircle.progress = progress



                    //it <999 && global_time_control==false

                    if(it == 0L ){
                        timeText.text = "00 : 00"

                        if(global_time_type == Times.START_TIME){
                            val date = System.currentTimeMillis()
                            global_time_control = true
                            viewModel.insertPomodoro(Pomodoro(finished_date_milis = date))
                        }
                    }

               // }
                if(graphState){
                    leftGraph = it
                    graphState = false
                }


            })
    }

    override fun onDestroy() {
        super.onDestroy()
        clearService()
    }
    private fun clearService(){
        serviceIntent?.let {
            requireActivity().stopService(it)
        }
    }
}