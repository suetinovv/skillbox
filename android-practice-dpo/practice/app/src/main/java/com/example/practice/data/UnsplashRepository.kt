package com.example.practice.data

import com.example.practice.api.UnsplashApi
import com.example.practice.data.collections.CollectionInfo
import com.example.practice.data.collections.CollectionsItem
import com.example.practice.data.foto_like.PhotoLike
import com.example.practice.data.photo.PhotoItem
import com.example.practice.data.photo_info.PhotoInfo
import com.example.practice.data.user.UserInfo

class UnsplashRepository(private val unsplashApi: UnsplashApi) {
    suspend fun getUserInfo(): UserInfo {
        return unsplashApi.getUserInfo()
    }

    suspend fun getListPhoto(page: Int): List<PhotoItem>{
        return unsplashApi.getListPhoto(page)
    }
    suspend fun getListUserPhoto(page: Int, userName: String): List<PhotoItem>{
        return unsplashApi.getListUserPhoto(userName, page)
    }
    suspend fun getListUserLikesPhoto(page: Int, userName: String): List<PhotoItem>{
        return unsplashApi.getListUserLikesPhoto(userName, page)
    }
    suspend fun getListUserCollections(page: Int, userName: String): List<CollectionsItem>{
        return unsplashApi.getListUserCollections(userName, page)
    }

    suspend fun getSearchListPhoto(page: Int, query: String): List<PhotoItem>{
        return unsplashApi.getSearchListPhoto(page, query).results
    }

    suspend fun getListCollections(page: Int): List<CollectionsItem>{
        return unsplashApi.getListCollections(page)
    }

    suspend fun getPhoto(id: String): PhotoInfo {
        return unsplashApi.getPhoto(id)
    }

    suspend fun getCollectionInfo(id: String): CollectionInfo {
        return unsplashApi.getCollectionInfo(id)
    }

    suspend fun getCollectionPhoto(id: String, page: Int): List<PhotoItem> {
        return unsplashApi.getCollectionPhoto(id, page)
    }

    suspend fun setLike(id: String): PhotoLike {
        return unsplashApi.setLike(id)
    }

    suspend fun unlike(id: String): PhotoLike {
        return unsplashApi.unlike(id)
    }


}