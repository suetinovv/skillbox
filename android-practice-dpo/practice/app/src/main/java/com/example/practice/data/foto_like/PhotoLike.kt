package com.example.practice.data.foto_like

import com.example.practice.data.photo.PhotoItem
import com.example.practice.data.photo.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoLike(
    val photo: PhotoItem,
    val user: User
)