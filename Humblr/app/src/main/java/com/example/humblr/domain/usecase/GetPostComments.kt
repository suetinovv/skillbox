package com.example.humblr.domain.usecase

import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.CommentListItem
import javax.inject.Inject

class GetPostComments @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(post: String)
            : List<CommentListItem> {
        return remoteRepository.getPostComments(post)
    }
}
