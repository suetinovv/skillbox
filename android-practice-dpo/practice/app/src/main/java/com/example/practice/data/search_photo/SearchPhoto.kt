package com.example.practice.data.search_photo

import com.example.practice.data.photo.PhotoItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPhoto(
    val results: List<PhotoItem>,
    val total: Int,
    val total_pages: Int
)