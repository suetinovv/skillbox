package com.example.practice.collection_paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.State
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.collections.CollectionsItem
import kotlinx.coroutines.flow.MutableStateFlow

class CollectionUserPagingSource(
    private val state: MutableStateFlow<State>,
    private val userName: String,
    private val repository: UnsplashRepository
    ) : PagingSource<Int, CollectionsItem>() {
    override fun getRefreshKey(state: PagingState<Int, CollectionsItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionsItem> {
        val page = params.key ?: FIRST_PAGE
        kotlin.runCatching {
            state.value = State.Loading
            repository.getListUserCollections(page, userName)
        }.fold(
            onSuccess = {
                state.value = State.Success
                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + FIRST_PAGE
                )
            },
            onFailure = {
                state.value = State.Error(it)
                return LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}