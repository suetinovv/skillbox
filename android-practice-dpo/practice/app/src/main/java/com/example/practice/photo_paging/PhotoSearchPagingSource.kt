package com.example.practice.photo_paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.App
import com.example.practice.State
import com.example.practice.data.HistoryRepository
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.photo.HistoryPhoto
import com.example.practice.data.photo.HistoryPhotoDao
import com.example.practice.data.photo.HistoryUser
import com.example.practice.data.photo.PhotoItem
import kotlinx.coroutines.flow.MutableStateFlow

class PhotoSearchPagingSource(
    private val state: MutableStateFlow<State>,
    private val repository: UnsplashRepository,
    private val historyRepository: HistoryRepository,
    private val query: String
) : PagingSource<Int, PhotoItem>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val page = params.key ?: FIRST_PAGE
        kotlin.runCatching {
            state.value = State.Loading
            repository.getSearchListPhoto(page, query)
        }.fold(
            onSuccess = {
                kotlin.runCatching {
                    state.value = State.Success
                    it.forEach { item ->
                        historyRepository.insertPhoto(item)
                    }
                }
                println(456)
                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + FIRST_PAGE
                )
            },
            onFailure = {
                println(789)
                val tempList = historyRepository.getHistoryPhoto(page)
                state.value = State.Error(it)
                return LoadResult.Page(
                    data = tempList,
                    prevKey = null,
                    nextKey = if (tempList.isEmpty()) null else page + FIRST_PAGE
                )
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}