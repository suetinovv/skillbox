package com.example.recycler.photolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recycler.models.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel private constructor(
    private val repository: PhotoListRepository
) : ViewModel() {
    constructor() : this(PhotoListRepository())

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos = _photos.asStateFlow()

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getPhotos()
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