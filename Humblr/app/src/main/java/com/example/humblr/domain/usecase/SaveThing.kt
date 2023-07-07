package com.example.humblr.domain.usecase

import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class SaveThing @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(post: String?) {
        return remoteRepository.saveThing(post)
    }
}

