package com.example.humblr.ui.subreddit_description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.domain.model.LoadingState
import com.example.humblr.domain.model.Subreddit
import com.example.humblr.domain.usecase.GetSubredditInfo
import com.example.humblr.domain.usecase.GetSubscribed
import com.example.humblr.domain.usecase.GetUnsubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubredditDescriptionViewModel @Inject constructor(
    private val getSubredditInfo: GetSubredditInfo,
    private val getSubscribed: GetSubscribed,
    private val getUnsubscribed: GetUnsubscribed
) : ViewModel() {

    private val _loadingState = MutableStateFlow<LoadingState<Any?>>(LoadingState.Loading())
    val loadingState = _loadingState.asStateFlow()
    private val _subscribeState = MutableStateFlow(false)
    val subscribeState = _subscribeState.asStateFlow()
    private val _subredditInfo = MutableStateFlow<Subreddit?>(null)
    val subredditInfo = _subredditInfo.asStateFlow()

    fun loadSubredditInfo(id: String): Subreddit? {

        var subreddit: Subreddit? = null
        viewModelScope.launch {
            kotlin.runCatching {
                getSubredditInfo(id)

            }.onSuccess {
                _loadingState.value = LoadingState.Success()
                subreddit = it
                _subscribeState.value = it.userIsSubscriber
                _subredditInfo.value = it
            }.onFailure {
                _loadingState.value = LoadingState.Error()
            }
        }
        return subreddit
    }

    fun getUserSubscribed(subreddit: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getSubscribed(subreddit)
            }.onSuccess {
                _subscribeState.value = true

            }
        }
    }

    fun getUserUnsubscribed(subreddit: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getUnsubscribed(subreddit)
            }.onSuccess {
                _subscribeState.value = false
            }
        }
    }
}