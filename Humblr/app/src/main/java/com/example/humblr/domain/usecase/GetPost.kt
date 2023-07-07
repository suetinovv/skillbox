package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.PostItem
import javax.inject.Inject

class GetPost @Inject constructor(private val remoteRepository: RemoteRepository) {
        suspend operator fun invoke(post: String)
                : PostItem {
            return remoteRepository.getPost(post)
        }
    }
