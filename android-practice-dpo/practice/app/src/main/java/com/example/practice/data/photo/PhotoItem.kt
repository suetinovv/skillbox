package com.example.practice.data.photo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoItem(
    val id: String,
    val urls: Urls,
    val likes: Int,
    var liked_by_user: Boolean,
    val user: User
)