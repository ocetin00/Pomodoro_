package com.oguzhancetin.pomodorotimer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.OffsetDateTime

@Entity
data class Pomodoro(

 @PrimaryKey(autoGenerate = true) val uid:Int? = null,
 @ColumnInfo(name = "finished_date") val finished_date_milis:Long


) {
}