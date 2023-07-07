package com.example.weather.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history WHERE name_city LIKE :name_city")
    suspend fun getHistory(name_city: String): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: History)

    @Query("SELECT DISTINCT name_city FROM history")
    suspend fun getAllCities(): List<String>

    @Query("DELETE FROM history")
    suspend fun clear()

    @Update
    suspend fun update(history: History)
}