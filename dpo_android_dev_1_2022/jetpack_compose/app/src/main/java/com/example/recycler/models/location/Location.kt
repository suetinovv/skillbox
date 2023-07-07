package com.example.recycler.models.location

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)