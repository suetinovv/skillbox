package com.example.practice.ui.collections

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.App
import com.example.practice.R
import com.example.practice.State
import com.example.practice.collection_paging.PhotoCollectionPagingSource
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.collections.CollectionInfo
import com.example.practice.data.photo.PhotoItem
import com.example.practice.data.user.UserInfo
import com.example.practice.photo_paging.PhotoPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CollectionsPhotoViewModel(private val unsplashRepository: UnsplashRepository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _pagedPhotos = MutableStateFlow<PagingData<PhotoItem>>(PagingData.empty())
    val pagedPhotos: StateFlow<PagingData<PhotoItem>> = _pagedPhotos

    private val _data = MutableLiveData<CollectionInfo>(null)
    val data: LiveData<CollectionInfo> = _data

    fun load(id: String){
        loadInfo(id)
        loadPhoto(id)
    }

    private fun loadInfo(id: String) {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.getCollectionInfo(id)
            }.fold(
                onSuccess = {
                    _data.value = it },
                onFailure = {

                }
            )
        }
    }



    private fun loadPhoto(id: String) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    PhotoCollectionPagingSource(
                        id,
                        _state,
                        App.component.getUnsplashRepository(),
                        App.component.getHistoryRepository()
                    )
                }
            ).flow.cachedIn(viewModelScope).collectLatest { pagingData ->
                _pagedPhotos.value = pagingData
            }
        }
    }

    fun setLike(id: String) {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.setLike(id)
            }.fold(
                onSuccess = {

                },
                onFailure = {
                    Toast.makeText(
                        App.context,
                        R.string.text_error_load,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            )
        }
    }

    fun unlike(id: String){
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.unlike(id)
            }.fold(
                onSuccess = {

                },

                onFailure = {
                    Toast.makeText(
                        App.context,
                        R.string.text_error_load,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            )
        }
    }
}