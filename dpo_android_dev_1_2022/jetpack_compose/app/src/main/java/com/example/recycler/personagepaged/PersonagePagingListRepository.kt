package com.example.recycler.personagepaged

import com.example.recycler.api.retrofit
import com.example.recycler.models.personage.Personage

class PersonagePagingListRepository {
    suspend fun getPersonageList(page: Int): List<Personage> {
        return retrofit.getPersonageList(page).results
    }

    suspend fun getPersonage(id: Int): Personage {
        return retrofit.getPersonage(id)
    }
}