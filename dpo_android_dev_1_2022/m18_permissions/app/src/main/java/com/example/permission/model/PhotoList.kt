package com.example.permission.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoList(
    val photos: List<Photo>
)