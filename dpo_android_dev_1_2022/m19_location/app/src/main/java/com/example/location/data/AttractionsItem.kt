package com.example.location.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttractionsItem(
    val dist: Double,
    val kinds: String,
    val name: String,
    val osm: String = "",
    val point: Point,
    val rate: Int,
    val wikidata: String = "",
    val xid: String
)