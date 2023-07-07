package com.example.practice.data.photo_info

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Position(
    val latitude: Double?,
    val longitude: Double?
)