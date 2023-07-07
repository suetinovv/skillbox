package com.example.recycler.locationpaget

import com.example.recycler.api.retrofit
import com.example.recycler.models.location.Location


class LocationListRepository {
    suspend fun getLocationList(page: Int): List<Location> {
        return retrofit.getLocationsList(page).results
    }
}