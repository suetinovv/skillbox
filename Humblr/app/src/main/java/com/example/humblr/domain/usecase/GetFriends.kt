package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.Friend
import javax.inject.Inject

class GetFriends @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(): List<Friend> {
        return remoteRepository.getFriends()
    }
}