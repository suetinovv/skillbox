package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class GetSubscribed @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(id: String?) {
        remoteRepository.getSubscribed(id)
    }
}