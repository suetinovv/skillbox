package com.example.location.domain

import com.example.location.data.AttractionRepository
import com.example.location.data.AttractionsItem
import javax.inject.Inject

class GetAttractionsUseCase @Inject constructor(private val attractionRepository: AttractionRepository) {

    fun execute(lon: Double, lat: Double): List<AttractionsItem> {
        return attractionRepository.getAttractions(lon, lat)
    }
}