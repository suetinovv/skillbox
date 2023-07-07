package com.example.weather

import android.app.Application
import androidx.room.Room
import com.example.weather.data.AppDatabase
import com.example.weather.di.DaggerComponent
import com.example.weather.di.DaggerDaggerComponent

class App : Application() {

    companion object {
        lateinit var component: DaggerComponent
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerDaggerComponent.builder().build()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}