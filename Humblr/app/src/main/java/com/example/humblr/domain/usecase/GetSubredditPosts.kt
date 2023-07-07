package com.example.humblr.domain.usecase

import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.SubredditPostsItem
import javax.inject.Inject

class GetSubredditPosts @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(subreddit: String): List<SubredditPostsItem> {
        return remoteRepository.getSubredditPosts(subreddit)
    }
}