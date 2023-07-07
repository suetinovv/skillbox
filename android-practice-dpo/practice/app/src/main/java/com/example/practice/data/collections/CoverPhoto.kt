package com.example.practice.data.collections

import com.example.practice.data.photo.Urls
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoverPhoto(
    val urls: Urls,
)