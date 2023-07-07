package com.example.weather

import com.example.weather.data.HistoryRepository
import com.example.weather.ui.cities.CitiesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CitiesViewModelTest {

    private lateinit var viewModel: CitiesViewModel
    private lateinit var mockRepository: HistoryRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        mockRepository = mockk<HistoryRepository>()
        viewModel = CitiesViewModel(mockRepository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when loadListCities is called then list should emit a list of cities`() {
        val expectedCities = listOf("Ковров", "Владимир", "Москва")
        coEvery { mockRepository.getAllCities() } returns expectedCities

        runBlocking {
            launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
                viewModel.loadListCities()
                delay(4000)
            }
        }

        val actualCities = viewModel.list.value

        assertEquals(expectedCities, actualCities)
    }

}


