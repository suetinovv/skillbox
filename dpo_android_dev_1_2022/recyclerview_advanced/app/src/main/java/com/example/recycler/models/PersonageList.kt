package com.example.recycler.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonageList(
    val info: Info,
    val results: List<Personage>
)