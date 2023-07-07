package com.example.humblr.data.remote.models

import com.example.humblr.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val data: UserData
) {
    fun toUser(): User {
        return User(
            name = this.data.name,
            created = this.data.created,
            isFriend = this.data.isFriend,
            icon = this.data.iconImg,
            karma = this.data.totalKarma
        )
    }
}

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "is_friend") val isFriend: Boolean?,
    @Json(name = "icon_img") val iconImg: String?,
    @Json(name = "total_karma") val totalKarma: Long?,
    val name: String?,
    val created: Double?,
    @Json(name = "snoovatar_img") val snoovatarImg: String?,
)


