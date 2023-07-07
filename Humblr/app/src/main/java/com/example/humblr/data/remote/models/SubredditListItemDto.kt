package com.example.humblr.data.remote.models

import com.example.humblr.domain.model.SubredditListItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubredditListItemDto(
    val data: Data
) {
    fun toSubredditList(): List<SubredditListItem> {
        return this.data.children.map { item ->
            SubredditListItem(
                author = item.data.subredditNamePrefixed,
                title = item.data.title,
                img = item.data.preview?.images?.first()?.source?.url,
                id = item.data.subreddit,
                subscribed = false
            )
        }
    }
}

@JsonClass(generateAdapter = true)
data class Data(
    val children: List<ChildA>,
    val after: String?,
)

@JsonClass(generateAdapter = true)
data class ChildA(
    val kind: String?, val data: ChildData
)

@JsonClass(generateAdapter = true)
data class ChildData(
    val subreddit: String?,
    val title: String?,
    @Json(name = "subreddit_name_prefixed") val subredditNamePrefixed: String?,
    val name: String?,
    val preview: Preview?,
    @Json(name = "subreddit_id") val subredditId: String?
)


@JsonClass(generateAdapter = true)
data class Preview(
    val images: List<Image>,
)

@JsonClass(generateAdapter = true)
data class Image(
    val source: ResizedIcon,
    val resolutions: List<ResizedIcon>,
)

@JsonClass(generateAdapter = true)
data class ResizedIcon(
    val url: String, val width: Long, val height: Long
)

