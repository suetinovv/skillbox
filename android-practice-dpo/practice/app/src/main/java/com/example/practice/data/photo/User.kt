package com.example.practice.data.photo

import com.example.practice.data.user.ProfileImage
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  User(
    val id: String,
    val username: String,
    val name: String,
    val profile_image: ProfileImage,
    val bio: Any?
)