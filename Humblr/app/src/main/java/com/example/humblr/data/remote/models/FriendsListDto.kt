package com.example.humblr.data.remote.models

import com.example.humblr.domain.model.Friend
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendsListDto(
    val kind: String, val data: FriendsData
) {
    fun toFriendsList(): List<Friend> {
        val friendsList = mutableListOf<Friend>()
        this.data.children.onEach {
            friendsList.add(
                    Friend(
                        name = it.name, created = it.date, icon = null
                    )
                )
        }
        return friendsList
    }
}

@JsonClass(generateAdapter = true)
data class FriendsData(
    val children: List<Child>
)

@JsonClass(generateAdapter = true)
data class Child(
    val date: Double,
    val name: String,
)

