package com.example.practice.collection_paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.App
import com.example.practice.State
import com.example.practice.data.collections.CollectionsItem
import kotlinx.coroutines.flow.MutableStateFlow

class CollectionPagingSource(private val state: MutableStateFlow<State>) : PagingSource<Int, CollectionsItem>() {
    private val repository = App.component.getUnsplashRepository()
    override fun getRefreshKey(state: PagingState<Int, CollectionsItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionsItem> {
        val page = params.key ?: FIRST_PAGE
        kotlin.runCatching {
            state.value = State.Loading
            repository.getListCollections(page)
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