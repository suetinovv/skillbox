package com.example.recycler.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoList(
    val photos: List<Photo>
)