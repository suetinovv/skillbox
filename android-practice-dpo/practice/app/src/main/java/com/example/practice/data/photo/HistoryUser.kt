package com.example.practice.data.photo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historyUser")
data class HistoryUser(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "profile_image")
    val profile_image: String,
)
