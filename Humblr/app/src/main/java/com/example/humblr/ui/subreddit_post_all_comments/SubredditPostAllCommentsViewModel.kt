package com.example.humblr.ui.subreddit_post_all_comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.domain.model.CommentListItem
import com.example.humblr.domain.model.LoadingState
import com.example.humblr.domain.usecase.GetPostComments
import com.example.humblr.domain.usecase.SaveThing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubredditPostAllCommentsViewModel @Inject constructor(
    private val getPostComments: GetPostComments,
    private val saveThing: SaveThing,
) : ViewModel() {

    private val _loadingState = MutableStateFlow<LoadingState<Any?>>(LoadingState.Loading())
    val loadingState = _loadingState.asStateFlow()
    private val _comments = MutableStateFlow<List<CommentListItem>>(emptyList())
    val comments = _comments.asStateFlow()

    fun saveSingleComment(id: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching { saveThing(id) }.onSuccess {
                onSuccess()
            }.onFailure {
                onFailure()
                println(it.stackTrace)
                println(it.cause)
                println(it.localizedMessage)
            }
        }
    }

    fun loadComments(post: String) {
        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading()
            kotlin.runCatching {

                getPostComments(post)
            }.onSuccess {
                _loadingState.value = LoadingState.Success()
                _comments.value = it
            }.onFailure {
                println(it.localizedMessage)
                _loadingState.value = LoadingState.Error()
            }
        }
    }
}