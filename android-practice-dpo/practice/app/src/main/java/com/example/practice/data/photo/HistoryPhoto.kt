package com.example.practice.data.photo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historyPhoto")
data class HistoryPhoto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "likes")
    val likes: Int,
    @ColumnInfo(name = "liked_by_user")
    val liked_by_user: Boolean,
    @ColumnInfo(name = "urls_image")
    val urls_image: String,
    @ColumnInfo(name = "id_user")
    val id_user: String,
)

