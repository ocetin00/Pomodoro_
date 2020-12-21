package com.oguzhancetin.pomodorotimer.fragment

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.oguzhancetin.pomodorotimer.util.leftTime
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
    private  var whichTimeStart = 1L

    private var globalLeft = 10000L
    private var global_time_type = Times.START_TIME
    private var global_time_control = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        progressCircle = binding.progress
        progressCircle.progress = progress
        //val viewmodelFactory = FragmentMainViewmodelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this).get(FragmentMainViewmodel::class.java)
        //viewModel.deleteAlldata()
        mCalendar = Calendar.getInstance()

        timeText = binding.textViewTimeLeft







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
        val longTime = TimesSharedPreferences.getSharred(requireActivity())?.getLong(Times.START_TIME.name,Times.START_TIME.time)
        val longTimeString = (longTime!!/60000).toString()+": "+(((longTime%60000)/1000)).toString()
        timeText.text = longTimeString
        Log.e("current",timeText.toString() +"  dsda")




        viewModel.allPomodoro.observe(viewLifecycleOwner, Observer {
            it.forEach {
                mCalendar.timeInMillis = (it.finished_date_milis)
                Log.e("pomodoro",mCalendar.get(Calendar.DAY_OF_MONTH).toString()+"/"+mCalendar.get(Calendar.MONDAY).toString())
            }
        })






        return binding.root
    }

    @SuppressLint("LongLogTag")
    fun startTimeService(time: Times){

        global_time_type = time
        global_time_control = false;

        var firstTrigger = false


         val serviceIntent = Intent(requireActivity(), MyService::class.java).also {
            it.putExtra("timeType", time)
            requireActivity().startService(it)
        }

            if (leftTime.hasObservers()) {
                leftTime.removeObservers(viewLifecycleOwner)
                Log.e("observer", "silindi")
            }

            leftTime.observe(requireActivity(), Observer {
                Log.e("trigger","true");
                if(firstTrigger){

                    val leftString = (it/60000).toString()+": "+(((it%60000)/1000)+1).toString()
                    timeText.text = leftString
                    globalLeft = it
                    progress =  (((it.toDouble()/whichTimeStart.toDouble())*100)).toInt()
                    progressCircle.progress = progress


                    Log.e("leftS",it.toString())
                    val date = System.currentTimeMillis()
                    Log.e("tarih",date.toString())
                    if(it <999 && global_time_control==false ){

                        if(time.name.equals(Times.START_TIME.name)){
                            val date = System.currentTimeMillis()
                            Log.e("ekledi","ekledi")
                            Log.e("tarih",date.toString())
                            global_time_control = true
                            viewModel.insertPomodoro(Pomodoro(finished_date_milis = date))
                        }
                    }

                }
                if(graphState){
                    whichTimeStart = it
                    graphState = false
                }
                firstTrigger = true

            })




    }


    override fun onDestroy() {
        super.onDestroy()
        serviceIntent?.let {
            requireActivity().stopService(it)
        }


    }




}