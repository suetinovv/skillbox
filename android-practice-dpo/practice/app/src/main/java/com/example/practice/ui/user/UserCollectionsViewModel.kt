package com.example.practice.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.App
import com.example.practice.State
import com.example.practice.collection_paging.CollectionUserPagingSource
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.collections.CollectionsItem
import com.example.practice.data.photo.PhotoItem
import com.example.practice.photo_paging.PhotoLikedPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserCollectionsViewModel(private val unsplashRepository: UnsplashRepository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _pagedCollection = MutableStateFlow<PagingData<CollectionsItem>>(PagingData.empty())
    val pagedCollection: StateFlow<PagingData<CollectionsItem>> = _pagedCollection

    fun load() {
        viewModelScope.launch() {
            kotlin.runCatching {
                unsplashRepository.getUserInfo()
            }.fold(
                onSuccess = {
                    Pager(
                        config = PagingConfig(pageSize = 10),
                        pagingSourceFactory = {
                            CollectionUserPagingSource(
                                _state,
                                it.username,
                                App.component.getUnsplashRepository()
                            )
                        }
                    ).flow.cachedIn(viewModelScope).collectLatest { pagingData ->
                        _pagedCollection.value = pagingData
                    } },
                onFailure = {

                }
            )
        }

    }
}