package com.example.permission.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permission.data.PhotoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraModel(
    private val photoDao: PhotoDao
) : ViewModel() {


    fun savePhotos(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                photoDao.insert(photo)
            }
        }
    }
}