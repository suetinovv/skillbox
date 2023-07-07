package com.example.recycler.models.location

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationList(
    val info: Info,
    val results: List<Location>
)