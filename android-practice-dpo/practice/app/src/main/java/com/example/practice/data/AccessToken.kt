package com.example.practice.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessToken(
    val access_token: String,
    val created_at: Long,
    val refresh_token: String,
    val scope: String,
    val token_type: String
)