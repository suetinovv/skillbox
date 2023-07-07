package com.example.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word WHERE count > 0 LIMIT 5 ")
    fun getFiveWord(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE id LIKE :id")
    suspend fun getWord(id: String): List<Word>

    @Insert()
    suspend fun insert(word: Word)

    @Query("DELETE FROM word")
    suspend fun clear()

    @Update
    suspend fun update(word: Word)

}