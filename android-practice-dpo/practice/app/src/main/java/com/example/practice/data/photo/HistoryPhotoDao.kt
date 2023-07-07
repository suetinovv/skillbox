package com.example.practice.data.photo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoryPhotoDao {
    @Query("SELECT * FROM historyPhoto LIMIT 10 OFFSET :offset")
    suspend fun getHistoryPhoto(offset: Int): List<HistoryPhoto>

    @Query("SELECT * FROM historyUser WHERE id LIKE :id_user ")
    suspend fun getHistoryUser(id_user: String): HistoryUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(historyPhoto: HistoryPhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(historyUser: HistoryUser)

//    @Query("SELECT DISTINCT name_city FROM history")
//    suspend fun getAllCities(): List<String>

    @Query("DELETE FROM historyPhoto")
    suspend fun cleanHistoryPhoto()

    @Query("DELETE FROM historyUser")
    suspend fun cleanHistoryUser()

    @Update
    suspend fun updatePhoto(historyPhoto: HistoryPhoto)

    @Update
    suspend fun updateUser(historyUser: HistoryUser)
}