package com.example.humblr.domain.model

data class Subreddit(
    val bannerImg: String?,
    val iconImg: String?,
    val displayName: String?,
    val description: String?,
    val subscribers: Long?,
    val title: String?,
    val userIsSubscriber: Boolean,
    val created: Long?
)