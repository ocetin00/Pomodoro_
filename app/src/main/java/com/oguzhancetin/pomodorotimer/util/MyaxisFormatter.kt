package com.oguzhancetin.pomodorotimer.util

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class MyaxisFormatter: ValueFormatter() {
    private val days = listOf("Mon","tue","wed","thu","fri","sat","sun")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return days.getOrNull(value.toInt()) ?: value.toString()
    }
}