package com.example.weather

import com.example.weather.data.History
import com.example.weather.data.HistoryDao
import com.example.weather.data.HistoryRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryRepositoryTest {

    @Mock
    private lateinit var historyDao: HistoryDao

    private lateinit var historyRepository: HistoryRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        historyRepository = HistoryRepository(historyDao)
    }

    @Test
    fun `test getHistory`() {
        runBlocking {
            val expectedHistoryList =
                listOf(
                    History(
                        city_and_date = "Moscow_11-04-2023",
                        name_city = "Moscow",
                        date = "11-04-2023",
                        temp_c = "5.0",
                        text = "Sun",
                        localtime = "11-04-2023"
                    ), History(
                        city_and_date = "London_11-04-2023",
                        name_city = "London",
                        date = "11-04-2023",
                        temp_c = "10.0",
                        text = "Sun",
                        localtime = "11-04-2023"
                    )
                )
            Mockito.`when`(historyDao.getHistory("testQuery")).thenReturn(expectedHistoryList)
            val result = historyRepository.getHistory("testQuery")
            assertEquals(expectedHistoryList, result)
        }
    }

    @Test
    fun `test insert`() {
        runBlocking {
            val history = History(
                city_and_date = "Moscow_11-04-2023",
                name_city = "Moscow",
                date = "11-04-2023",
                temp_c = "5.0",
                text = "Sun",
                localtime = "11-04-2023"
            )
            historyRepository.insert(history)
            Mockito.verify(historyDao).insert(history)
        }
    }

    @Test
    fun `test getAllCities`() {
        runBlocking {
            val expectedCitiesList = listOf("Moscow", "London", "Paris")
            Mockito.`when`(historyDao.getAllCities()).thenReturn(expectedCitiesList)
            val result = historyRepository.getAllCities()
            assertEquals(expectedCitiesList, result)
        }
    }
}
