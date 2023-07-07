package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.CommentListItem
import javax.inject.Inject

class GetSavedComments @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(userName: String?): List<CommentListItem> {
        return remoteRepository.getSavedComments(userName)
    }
}