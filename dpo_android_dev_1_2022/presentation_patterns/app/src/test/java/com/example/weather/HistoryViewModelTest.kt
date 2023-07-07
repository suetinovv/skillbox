package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.data.History
import com.example.weather.data.HistoryRepository
import com.example.weather.ui.history.HistoryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var historyRepository: HistoryRepository
    private lateinit var viewModel: HistoryViewModel

    private val testHistoryList = listOf(
        History(
            city_and_date = "Ковров_11-04-2023",
            name_city = "Ковров",
            date = "11-04-2023",
            temp_c = "5.0",
            text = "Sun",
            localtime = "11-04-2023"
        )
    )

    @Before
    fun setup() {
        historyRepository = mockk()
        viewModel = HistoryViewModel(historyRepository)
    }

    @Test
    fun `loadListCities with successful response`() {
        // Given
        coEvery { historyRepository.getHistory(any()) } returns testHistoryList

        // When
        runBlocking(Dispatchers.IO) {
            viewModel.loadListCities("Ковров")
            delay(4000)
        }

        // Then
        runBlocking {
            assertEquals(testHistoryList, viewModel.listHistory.first())
        }
    }

    @Test
    fun `loadListCities with error response`() {
        // Given
        coEvery { historyRepository.getHistory(any()) } throws Exception()

        // When
        runBlocking(Dispatchers.IO) {
            viewModel.loadListCities("Ковров")
        }

        // Then
        runBlocking {
            assertEquals(emptyList<History>(), viewModel.listHistory.first())
        }
    }
}



