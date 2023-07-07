package com.example.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val info: Info,
    val results: List<Result>
)