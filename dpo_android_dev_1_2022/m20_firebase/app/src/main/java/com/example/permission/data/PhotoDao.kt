package com.example.permission.data

import androidx.room.*
import com.example.permission.model.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo")
    fun getListPhoto(): List<Photo>

    @Insert()
    suspend fun insert(Photo: Photo)

    @Query("DELETE FROM photo")
    suspend fun clear()

}