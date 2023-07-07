package com.example.practice.data.photo_info

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    val title: String?,
    val type: String?
)