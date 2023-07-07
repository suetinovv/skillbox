package com.example.recycler.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)

@JsonClass(generateAdapter = true)
data class Camera(
    val full_name: String,
    val id: Int,
    val name: String,
    val rover_id: Int
)

@JsonClass(generateAdapter = true)
data class Rover(
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val name: String,
    val status: String
)