package com.example.weather.data

class HistoryRepository(private val historyDao: HistoryDao) {


    suspend fun getHistory(q: String): List<History> {
        return historyDao.getHistory(q)
    }

    suspend fun insert(history: History) {
        return historyDao.insert(history)
    }

    suspend fun getAllCities(): List<String> {
        return historyDao.getAllCities()
    }


}