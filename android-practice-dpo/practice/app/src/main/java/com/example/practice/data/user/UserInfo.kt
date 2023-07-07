package com.example.practice.data.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    val id: String,
    val username: String,
    val email: String,
    val name: String,
    val links: Links,
    val profile_image: ProfileImage,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val downloads: Int,
    val photos: List<Any>?,
    val location: Any?,
    val bio: Any?
)