package com.example.permission.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permission.data.PhotoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val photoDao: PhotoDao
) : ViewModel() {

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos = _photos.asStateFlow()


    fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                photoDao.getListPhoto()
            }.fold(
                onSuccess = { _photos.value = it },
                onFailure = {
                    Log.d("PhotosListViewModel", it.message ?: "")
                    loadPhotos()
                }
            )
        }
    }
}