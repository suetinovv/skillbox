package com.example.practice.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.State
import com.example.practice.collection_paging.CollectionPagingSource
import com.example.practice.data.collections.CollectionsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CollectionsViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    val pagedCollection: Flow<PagingData<CollectionsItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { CollectionPagingSource(_state) }
    ).flow.cachedIn(viewModelScope)
}