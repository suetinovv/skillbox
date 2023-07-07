package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.User
import javax.inject.Inject

class GetCurrentUserInfo @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(): User {
        return remoteRepository.getCurrentUser()
    }
}
