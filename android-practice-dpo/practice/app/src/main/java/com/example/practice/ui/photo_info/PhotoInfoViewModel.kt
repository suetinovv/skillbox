package com.example.practice.ui.photo_info

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.App
import com.example.practice.R
import com.example.practice.State
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.photo.Urls
import com.example.practice.data.photo.User
import com.example.practice.data.photo_info.Exif
import com.example.practice.data.photo_info.Links
import com.example.practice.data.photo_info.Location
import com.example.practice.data.photo_info.PhotoInfo
import com.example.practice.data.photo_info.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoInfoViewModel(private val unsplashRepository: UnsplashRepository) : ViewModel() {
    private val _data = MutableStateFlow<PhotoInfo?>(null)
    val data = _data.asStateFlow()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()


    fun loadPhotoInfo(id: String) {
        _state.value = State.Loading
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.getPhoto(id)
            }.fold(
                onSuccess = {
                    _data.value = it
                    _state.value = State.Success
                },
                onFailure = {
                    _data.value = null
                    _state.value = State.Error(it)
                }
            )
        }
    }
    fun setLike(id: String) {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.setLike(id)
            }.fold(
                onSuccess = {
                    loadPhotoInfo(id)
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
                    loadPhotoInfo(id)
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