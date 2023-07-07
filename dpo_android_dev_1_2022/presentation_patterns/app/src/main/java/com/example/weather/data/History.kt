package com.example.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey
    @ColumnInfo(name = "city_and_date")
    val city_and_date: String,
    @ColumnInfo(name = "name_city")
    val name_city: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "temp_c")
    val temp_c: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "localtime")
    val localtime: String


) : ResultWeather