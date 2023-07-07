package com.example.humblr.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.domain.model.CommentListItem
import com.example.humblr.domain.model.LoadingState
import com.example.humblr.domain.model.PostItem
import com.example.humblr.domain.usecase.GetCurrentUserInfo
import com.example.humblr.domain.usecase.GetSavedComments
import com.example.humblr.domain.usecase.GetSavedPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getSavedPosts: GetSavedPosts,
    private val getSavedComments: GetSavedComments,
    private val getCurrentUserInfo: GetCurrentUserInfo
) : ViewModel() {

    private val _loadingState = MutableStateFlow<LoadingState<Any?>>(LoadingState.Loading())
    val loadingState = _loadingState.asStateFlow()
    private val _savedPosts = MutableStateFlow<List<PostItem>>(emptyList())
    val savedPosts = _savedPosts.asStateFlow()
    private val _savedComments = MutableStateFlow<List<CommentListItem>>(emptyList())
    val savedComments = _savedComments.asStateFlow()

    fun loadSavedComments() {
        _loadingState.value = LoadingState.Loading()
        viewModelScope.launch {
            kotlin.runCatching {
                getCurrentUserInfo()
            }.onSuccess {
                loadComments(it.name)
            }
        }
    }

    private fun loadComments(userName: String?) {
        viewModelScope.launch {

            kotlin.runCatching {
                getSavedComments(userName)

            }.onSuccess {
                _loadingState.value = LoadingState.Success()
                _savedComments.value = it
            }.onFailure {
                _loadingState.value = LoadingState.Error()
            }
        }

    }

    fun loadSavedPosts() {
        _loadingState.value = LoadingState.Loading()
        viewModelScope.launch {
            kotlin.runCatching {
                getCurrentUserInfo()
            }.onSuccess {
                loadPosts(it.name)
            }
        }
    }

    private fun loadPosts(userName: String?) {
        viewModelScope.launch {
            kotlin.runCatching {
                getSavedPosts(userName)
            }.onSuccess {
                _loadingState.value = LoadingState.Success()
                _savedPosts.value = it
            }.onFailure {
                _loadingState.value = LoadingState.Error()
            }
        }

    }
}