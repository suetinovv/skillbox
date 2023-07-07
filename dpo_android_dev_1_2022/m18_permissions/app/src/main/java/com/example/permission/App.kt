package com.example.permission

import android.app.Application
import androidx.room.Room
import com.example.permission.data.AppDatabase


class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}