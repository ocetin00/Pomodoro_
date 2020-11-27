package com.oguzhancetin.pomodorotimer.util

import java.io.Serializable

enum class Times(val time:Long): Serializable{
    START_TIME(1500000),
    SHORT_BREAK(300000),
    LONG_BREAK(900000),
}