package com.example.recycler.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recycler.State
import com.example.recycler.models.Personage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class PersonagePagingSource(private val state: MutableStateFlow<State>) : PagingSource<Int, Personage>() {
    private val repository = PersonagePagingListRepository()
    override fun getRefreshKey(state: PagingState<Int, Personage>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Personage> {
        val page = params.key ?: FIRST_PAGE
        kotlin.runCatching {
            state.value = State.Loading
            delay(1000)
            repository.getPersonageList(page)
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