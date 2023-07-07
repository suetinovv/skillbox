package com.example.clean.domain

import com.example.clean.data.UsefulActivitiesRepository
import com.example.clean.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(val usefulActivitiesRepository: UsefulActivitiesRepository) {

    fun execute(): UsefulActivity {
        return usefulActivitiesRepository.getUsefulActivity()
    }
}