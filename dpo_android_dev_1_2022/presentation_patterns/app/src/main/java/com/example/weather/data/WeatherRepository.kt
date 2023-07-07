package com.example.weather.data

import com.example.weather.api.WeatherApi

class WeatherRepository(private val weatherApi: WeatherApi) {
    suspend fun getWeather(q: String): Weather {
        return weatherApi.getWeather(q)
    }

}