package com.example.recycler.paged

import com.example.recycler.api.retrofit
import com.example.recycler.models.Personage

class PersonagePagingListRepository {
    suspend fun getPersonageList(page: Int): List<Personage> {
        return retrofit.personageList(page).results
    }
}