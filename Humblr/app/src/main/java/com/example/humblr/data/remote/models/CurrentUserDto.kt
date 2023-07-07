package com.example.humblr.data.remote.models

import com.example.humblr.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentUserDto(

    @Json(name = "icon_img") val iconImg: String,
    @Json(name = "total_karma") val totalKarma: Long,
    val name: String,
    val created: Double,
) {
    fun toUser(): User {
        return User(
            name = this.name,
            created = this.created,
            isFriend = null,
            icon = this.iconImg,
            karma = this.totalKarma
        )
    }
}
