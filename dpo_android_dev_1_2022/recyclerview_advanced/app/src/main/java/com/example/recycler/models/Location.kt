package com.example.recycler.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val url: String
)