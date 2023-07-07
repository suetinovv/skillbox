package com.example.practice.data.collections

import com.example.practice.data.photo.User
import com.example.practice.data.photo_info.Tag
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionInfo(
    val id: String,
    val title: String,
    val description: String,
    val total_photos: Int,
    val tags: List<Tag>,
    val user: User,
    val cover_photo: CoverPhoto,

    )