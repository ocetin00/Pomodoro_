package com.oguzhancetin.pomodorotimer

import android.content.Context
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.oguzhancetin.pomodorotimer.background.TimerBroadcast
import com.oguzhancetin.pomodorotimer.fragment.FragmentMain
import com.oguzhancetin.pomodorotimer.fragment.FragmentStat
import com.oguzhancetin.pomodorotimer.fragment.SettingsFragment
import com.oguzhancetin.pomodorotimer.util.MyPagerAdapter

class MainActivity : AppCompatActivity() {

    val timerBroadcastReceiver = TimerBroadcast()

    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setIcon(R.drawable.ic_baseline_timer_24)
        supportActionBar?.setTitle(" Pomodoro")


        viewPager = findViewById(R.id.pager)
        val fragmentMain = FragmentMain()
        val fragmentStat = FragmentStat()
        val fragmetnSettings = SettingsFragment()
        val fragmentList = ArrayList<Fragment>()

        fragmentList.add((fragmentMain))
        fragmentList.add(fragmentStat)
        fragmentList.add(fragmetnSettings)
        val pagerAdapter = MyPagerAdapter(this,fragmentList)
        viewPager.adapter = pagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val tabTitles = listOf("Pomodoro","Stat","Setttings")


        TabLayoutMediator(tabLayout,viewPager){tab,positon ->
            viewPager.currentItem = tab.position
            tab.text = tabTitles.get(positon)
        }.attach()


        val s:String? = null

        print(s)



    }
    fun print(s:String){
        print(s)
    }

    override fun onStart() {
        super.onStart()


        IntentFilter("com.oguzhancetin.pomodorotimer.SEND_TIME").also {
            registerReceiver(timerBroadcastReceiver,it)
        }

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(timerBroadcastReceiver)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }


}