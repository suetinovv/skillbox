package com.example.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Street(
    val name: String,
    val number: Int
)