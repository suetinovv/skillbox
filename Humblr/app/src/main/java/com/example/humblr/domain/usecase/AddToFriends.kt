package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class AddToFriends @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(userName: String?) {
        return remoteRepository.makeFriends(userName)
    }
}

