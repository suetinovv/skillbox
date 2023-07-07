package com.example.practice

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.practice.data.AppDatabase
import com.example.practice.di.DaggerComponent
import com.example.practice.di.DaggerDaggerComponent

class App : Application() {

    companion object {
        lateinit var component: DaggerComponent
        lateinit var accessToken: String
        lateinit var db: AppDatabase
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        val prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
        accessToken = prefs.getString("access_token", "")!!
        context = applicationContext

        component = DaggerDaggerComponent.builder().build()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }

}