package com.example.practice.data.photo_info

import com.example.practice.data.photo.Urls
import com.example.practice.data.photo.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoInfo(
    val id: String,
    val exif: Exif,
    val tags: List<Tag>?,
    val location: Location?,
    val user: User,
    val downloads: Int,
    val liked_by_user: Boolean,
    val likes: Int,
    val urls: Urls,
    val links: Links,
    )