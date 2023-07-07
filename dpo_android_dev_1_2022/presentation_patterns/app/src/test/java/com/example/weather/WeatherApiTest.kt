package com.example.weather

import com.example.weather.api.WeatherApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException

class WeatherApiTest {

    private val weatherApi = WeatherApi.create()

    @Test
    fun `test getWeather() with valid query`() = runBlocking {
        val weather = weatherApi.getWeather("Moscow")
        assertEquals("Moscow", weather.location.name)
    }

    @Test(expected = HttpException::class)
    fun `test getWeather() with invalid query`() = runBlocking<Unit> {
        weatherApi.getWeather("invalid_city_name")
    }
}
