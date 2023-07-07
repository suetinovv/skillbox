package com.example.recycler.models.personage

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonageList(
    val info: Info,
    val results: List<Personage>
)