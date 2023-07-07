package com.example.clean.data

import com.example.clean.entity.UsefulActivity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UsefulActivityDto(
    override val accessibility: Double,
    override val activity: String,
    override val key: String,
    override val link: String,
    override val participants: Int,
    override val price: Double,
    override val type: String
) : UsefulActivity {
}