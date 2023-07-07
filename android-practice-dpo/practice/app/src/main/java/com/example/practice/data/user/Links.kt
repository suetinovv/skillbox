package com.example.practice.data.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    val followers: String?,
    val following: String?,
    val html: String?,
    val likes: String?,
    val photos: String?,
    val portfolio: String?,
    val self: String?
)