package com.example.permission.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.permission.model.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}