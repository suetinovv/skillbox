package com.example.recycler.photolist

import com.example.recycler.api.retrofit
import com.example.recycler.models.Photo

class PhotoListRepository {
    suspend fun getPhotos(): List<Photo> {
        return retrofit.photos().photos
    }
}