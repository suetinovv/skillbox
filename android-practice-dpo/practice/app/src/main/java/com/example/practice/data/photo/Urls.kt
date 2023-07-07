package com.example.practice.data.photo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Urls(
    val small: String,
    val raw: String
)