package com.example.weather

import com.example.weather.api.WeatherApi
import com.example.weather.data.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherRepositoryTest {

    private val mockWeatherApi = mockk<WeatherApi>()
    private val weatherRepository = WeatherRepository(mockWeatherApi)

    @Test
    fun `getWeather() returns weather data from API`() = runBlocking {
        // Arrange
        val cityName = "London"
        val expectedWeather = Weather(
            current = Current(
                cloud = 1,
                condition = Condition(
                    code = 1,
                    icon = "String",
                    text = "String"
                ),
                feelslike_c = 1.0,
                feelslike_f = 1.0,
                gust_kph = 1.0,
                gust_mph = 1.0,
                humidity = 1,
                is_day = 1,
                last_updated = "String",
                last_updated_epoch = 1,
                precip_in = 1.0,
                precip_mm = 1.0,
                pressure_in = 1.0,
                pressure_mb = 1.0,
                temp_c = 1.0,
                temp_f = 1.0,
                uv = 1.0,
                vis_km = 1.0,
                vis_miles = 1.0,
                wind_degree = 1,
                wind_dir = "String",
                wind_kph = 1.0,
                wind_mph = 1.0

            ),
            location = Location(
                country = "String",
                lat = 1.0,
                localtime = "String",
                localtime_epoch = 1,
                lon = 1.0,
                name = "String",
                region = "String",
                tz_id = "String"
            )
        )
        coEvery { mockWeatherApi.getWeather(cityName) } returns expectedWeather

        // Act
        val actualWeather = weatherRepository.getWeather(cityName)

        // Assert
        assertEquals(expectedWeather, actualWeather)
    }

}
