package com.oguzhancetin.pomodorotimer.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter(fragmentActivity: FragmentActivity,val fragmentList: List<Fragment>): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return  fragmentList.get(position)
    }


}