package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class GetUnsubscribed @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(id: String?) {
        remoteRepository.getUnsubscribed(id)
    }
}