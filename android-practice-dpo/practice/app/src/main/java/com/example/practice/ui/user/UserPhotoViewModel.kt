package com.example.practice.ui.user

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.App
import com.example.practice.R
import com.example.practice.State
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.photo.PhotoItem
import com.example.practice.photo_paging.PhotoUserPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserPhotoViewModel(private val unsplashRepository: UnsplashRepository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _position = MutableStateFlow<Int>(-1)
    val position = _position.asStateFlow()

    private val _pagedPhotos = MutableStateFlow<PagingData<PhotoItem>>(PagingData.empty())
    val pagedPhotos: StateFlow<PagingData<PhotoItem>> = _pagedPhotos

    fun load() {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.getUserInfo()
            }.fold(
                onSuccess = {
                    Pager(
                        config = PagingConfig(pageSize = 10),
                        pagingSourceFactory = {
                            PhotoUserPagingSource(
                                _state,
                                App.component.getUnsplashRepository(),
                                App.component.getHistoryRepository(),
                                it.username
                            )
                        }
                    ).flow.cachedIn(viewModelScope).collectLatest { pagingData ->
                        _pagedPhotos.value = pagingData
                    } },
                onFailure = {

                }
            )
        }

    }


    fun setLike(id: String, position: Int) {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.setLike(id)
            }.fold(
                onSuccess = {
                    load()
                    _position.value = position
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

    fun unlike(id: String, position: Int){
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.unlike(id)
            }.fold(
                onSuccess = {
                    load()
                    _position.value = position
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