package com.example.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatItem(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)