package com.oguzhancetin.pomodorotimer.fragment

import android.graphics.Color
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.oguzhancetin.pomodorotimer.R
import com.oguzhancetin.pomodorotimer.database.Pomodoro
import com.oguzhancetin.pomodorotimer.databinding.FragmentStatBinding
import com.oguzhancetin.pomodorotimer.util.MyaxisFormatter
import com.oguzhancetin.pomodorotimer.viewmodel.FragmentMainViewmodelFactory
import com.oguzhancetin.pomodorotimer.viewmodel.FragmentStatusViewModel
import kotlinx.android.synthetic.main.fragment_stat.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList


class FragmentStat : Fragment() {

    private lateinit var fragmentStatusViewModel: FragmentStatusViewModel
    private lateinit var mLineChart: LineChart


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.e("stat","created")
        // Inflate the layout for this fragment
        val binding = FragmentStatBinding.inflate(inflater)


        //val viewModelFactory = FragmentMainViewmodelFactory(requireActivity().application)
        fragmentStatusViewModel = ViewModelProvider(this).get(FragmentStatusViewModel::class.java)

        mLineChart = binding.chart
        mLineChart.xAxis.valueFormatter = MyaxisFormatter()
        mLineChart.axisRight.isEnabled = false



        mLineChart.xAxis.setDrawGridLines(false)
        mLineChart.also {
            it.setTouchEnabled(false)
            it.xAxis.axisMaximum = 6f
            it.xAxis.axisMinimum = 0f
            it.axisLeft.axisMinimum = 0f
            it.axisLeft.axisMaximum = 6f
            it.xAxis.yOffset = 14f
          //  it.axisLeft.mAxisRange = 23f


        }

        fragmentStatusViewModel.allPomodoro.observe(viewLifecycleOwner, Observer { pomodoros ->
            pomodoros?.let {
                Log.e("sorted",it.size.toString())
                setGraphData(pomodoros)

            }

        })

        return binding.root
    }

    //set graph entries
    @RequiresApi(Build.VERSION_CODES.O)
    fun setGraphData(sortedPomodoros: List<Pomodoro>) {
        val today = LocalDateTime.now()
        val t2 =  LocalDateTime.ofInstant(Date(System.currentTimeMillis()).toInstant(), ZoneId.systemDefault())

        Log.e("today",today.year.toString()+" "+today.monthValue+today.dayOfWeek+"  // "+t2.dayOfWeek+" "+t2.monthValue)



        val days = arrayOf(0, 0, 0, 0, 0, 0, 0, 0)

        //sorted to obtain which day and added to days array
        sortedPomodoros.forEach {


            val pday = LocalDateTime.ofInstant(Date(it.finished_date_milis).toInstant(), ZoneId.systemDefault())

            Log.e("pday",pday.dayOfWeek.toString()+pday.monthValue)

            if(pday.year == today.year && pday.month == today.month){

                Log.e("value",pday.dayOfWeek.toString())
                when (pday.dayOfWeek.value.toString()) {

                    "1" -> days.set(0, days.get(0) + 1)
                    "2" -> days.set(1, days.get(1) + 1)
                    "3" -> days.set(2, days.get(5) + 1)
                    "4" -> days.set(3, days.get(3) + 1)
                    "5" -> days.set(4, days.get(4) + 1)
                    "6" -> days.set(5, days.get(5) + 1)
                    "7" -> days.set(6, days.get(6) + 1)
                }
            }
        }
        if(days.sortedArrayDescending().first() >  mLineChart.axisLeft.axisMaximum){
            mLineChart.axisLeft.axisMaximum = (days.sortedArrayDescending().first()+2)*1f
        }

        for(x in days){
            Log.e("daysnumber",x.toString())
        }


        val entries = ArrayList<Entry>()
        for(x in 0 until days.size-1){
            entries.add(Entry(x.toFloat(),days.get(x).toFloat()))
        }


        val dataSet = LineDataSet(entries,"Pomodoro Number").also {
            it.setColor(Color.parseColor("#FA5858"))


        }

        val lineData = LineData(dataSet).also {

            mLineChart.data = it
            val desc = Description()
            desc.text = ""
            mLineChart.description = desc
            mLineChart.invalidate()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("stat","destroy")
    }



}