package com.example.weather.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val current: Current,
    val location: Location
) : ResultWeather