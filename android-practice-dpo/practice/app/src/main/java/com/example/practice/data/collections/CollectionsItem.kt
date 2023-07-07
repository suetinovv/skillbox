package com.example.practice.data.collections

import com.example.practice.data.photo.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionsItem(
    val id: String,
    val title: String,
    val total_photos: Int,
    val user: User,
    val cover_photo: CoverPhoto?
)