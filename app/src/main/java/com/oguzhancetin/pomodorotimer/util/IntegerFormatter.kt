package com.oguzhancetin.pomodorotimer.util

import com.github.mikephil.charting.components.AxisBase


import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat


class IntegerFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return Math.round(value).toString();
    }
}
