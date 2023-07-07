package com.example.practice.data.photo_info

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val city: Any?,
    val country: Any?,
    val name: Any?,
    val position: Position?
)