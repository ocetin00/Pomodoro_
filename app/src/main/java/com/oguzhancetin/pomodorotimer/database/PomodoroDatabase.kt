package com.oguzhancetin.pomodorotimer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Pomodoro::class),version = 1)
public abstract class PomodoroDatabase: RoomDatabase(){

   abstract fun pomodoroDao(): PomodoroDao


    companion object{

        private var INSTANCE:PomodoroDatabase? = null

        fun getDatabase(context: Context): PomodoroDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PomodoroDatabase::class.java,
                    "pomodoroDb").build()

                INSTANCE = instance
                return instance
            }

        }
    }
}