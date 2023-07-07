package com.example.recycler.models.personage

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Origin(
    val name: String,
    val url: String
)