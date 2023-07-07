package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.Subreddit
import javax.inject.Inject

class GetSubredditInfo @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(subreddit: String): Subreddit {
        return remoteRepository.getSingleSubreddit(subreddit)
    }
}