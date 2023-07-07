package com.example.humblr.domain.usecase

import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class UnsaveThing @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(comment: String?) {
        return remoteRepository.unsaveThing(comment)
    }
}