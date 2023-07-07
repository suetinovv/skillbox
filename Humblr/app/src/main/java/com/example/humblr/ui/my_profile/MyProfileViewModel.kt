package com.example.humblr.ui.my_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.domain.model.Friend
import com.example.humblr.domain.model.LoadingState
import com.example.humblr.domain.model.User
import com.example.humblr.domain.usecase.GetCurrentUserInfo
import com.example.humblr.domain.usecase.GetFriends
import com.example.humblr.domain.usecase.GetSavedThings
import com.example.humblr.domain.usecase.GetUserInfo
import com.example.humblr.domain.usecase.UnsaveThing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getCurrentUserInfo: GetCurrentUserInfo,
    private val getUserInfo: GetUserInfo,
    private val getFriends: GetFriends,
    private val getSavedThings: GetSavedThings,
    private val unsaveThing: UnsaveThing
) : ViewModel() {

    private val _loadingState = MutableStateFlow<LoadingState<Any?>>(LoadingState.Loading())
    val loadingState = _loadingState.asStateFlow()
    private val _currentUserInfo = MutableStateFlow<User?>(null)
    val currentUserInfo = _currentUserInfo.asStateFlow()

    private val _friendsInfo = MutableStateFlow<List<Friend?>>(emptyList())
    val friendsInfo = _friendsInfo.asStateFlow()

    fun deleteSavedThings(
        onSuccess: () -> Unit, onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                _currentUserInfo.value?.name?.let { getSavedThings(it) }

            }.onSuccess {
                deleteThing(it, onSuccess, onFailure)
            }
        }
    }

    private fun deleteThing(
        list: List<String>?, onSuccess: () -> Unit, onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                list?.onEach { unsaveThing(it) }
            }.onSuccess {
                onSuccess()
            }.onFailure { onFailure() }
        }
    }

    fun loadCurrentUserInfo() {
        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading()
            kotlin.runCatching {
                getCurrentUserInfo()
            }.onSuccess {
                _currentUserInfo.value = it
            }.onFailure {
                _loadingState.value = LoadingState.Error()
            }
        }
    }

    fun loadFriends(onSuccess: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                getFriends()
            }.onSuccess {
                _friendsInfo.value = it
                loadFriendsIcons { onSuccess() }
            }.onFailure {}
        }
    }

    private fun loadFriendsIcons(onSuccess: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                _friendsInfo.value.onEach {
                    it?.icon = it?.name?.let { userName -> getUserInfo(userName).icon }
                }
            }.onSuccess {
                onSuccess()
                _loadingState.value = LoadingState.Success()
            }.onFailure {
                _loadingState.value = LoadingState.Error()
            }
        }
    }


}