package com.example.location.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Point(
    val lat: Double,
    val lon: Double
)