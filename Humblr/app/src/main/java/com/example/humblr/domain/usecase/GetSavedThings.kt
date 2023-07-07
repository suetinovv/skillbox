package com.example.humblr.domain.usecase


import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class GetSavedThings @Inject constructor(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(userName: String): List<String> {
        return remoteRepository.getSavedThings(userName)
    }
}