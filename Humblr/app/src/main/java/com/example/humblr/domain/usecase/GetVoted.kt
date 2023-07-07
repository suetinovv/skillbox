package com.example.humblr.domain.usecase

import com.example.humblr.data.remote.RemoteRepository
import javax.inject.Inject

class GetVoted @Inject constructor(private val remoteRepository: RemoteRepository) {
        suspend operator fun invoke(id: String, dir: Int) {
            remoteRepository.getVoted(id, dir)
        }
    }
