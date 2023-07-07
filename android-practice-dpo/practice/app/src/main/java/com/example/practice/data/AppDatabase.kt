package com.example.practice.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practice.data.photo.HistoryPhoto
import com.example.practice.data.photo.HistoryPhotoDao
import com.example.practice.data.photo.HistoryUser

@Database(entities = [HistoryPhoto::class, HistoryUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyPhotoDao(): HistoryPhotoDao
}