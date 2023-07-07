package com.example.practice.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.data.HistoryRepository
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.user.UserInfo
import kotlinx.coroutines.launch

class UserViewModel(
    private val unsplashRepository: UnsplashRepository,
    private val historyRepository: HistoryRepository
    ) : ViewModel() {

    private val _data = MutableLiveData<UserInfo>(null)
    val data: LiveData<UserInfo> = _data

    fun loadMeInfo() {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.getUserInfo()
            }.fold(
                onSuccess = {
                    _data.value = it },
                onFailure = {

                }
            )
        }
    }
    fun logOut(){
        viewModelScope.launch() {
            kotlin.runCatching {
                historyRepository.clean()
            }
        }
    }
}