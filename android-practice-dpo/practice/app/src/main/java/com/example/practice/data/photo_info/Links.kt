package com.example.practice.data.photo_info

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
)