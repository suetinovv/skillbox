package com.example.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather.di.DaggerComponent
import com.example.weather.di.DaggerDaggerComponent
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MyIntegrationTest {

    private lateinit var component: DaggerComponent

    @Before
    fun setUp() {
        component = DaggerDaggerComponent.builder().build()
    }

    @Test
    fun testWeatherRepository() {
        val weatherRepository = component.getWeatherRepository()
        assertNotNull(weatherRepository)

        // Тестирование методов WeatherRepository
        // ...
    }

    @Test
    fun testHistoryRepository() {
        val historyRepository = component.getHistoryRepository()
        assertNotNull(historyRepository)

        // Тестирование методов HistoryRepository
        // ...
    }
}