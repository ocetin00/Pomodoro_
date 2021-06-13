package com.oguzhancetin.pomodorotimer.util

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

val leftTime = MutableLiveData<Long>().default(1L)
