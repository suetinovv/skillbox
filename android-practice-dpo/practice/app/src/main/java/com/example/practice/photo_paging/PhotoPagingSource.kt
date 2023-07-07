package com.example.practice.photo_paging

import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.App
import com.example.practice.R
import com.example.practice.State
import com.example.practice.data.HistoryRepository
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.photo.HistoryPhoto
import com.example.practice.data.photo.HistoryPhotoDao
import com.example.practice.data.photo.HistoryUser
import com.example.practice.data.photo.PhotoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class PhotoPagingSource(
    private val state: MutableStateFlow<State>,
    private val repository: UnsplashRepository,
    private val historyRepository: HistoryRepository,
) : PagingSource<Int, PhotoItem>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val page = params.key ?: FIRST_PAGE
        kotlin.runCatching {
            state.value = State.Loading
            repository.getListPhoto(page)
        }.fold(
            onSuccess = {
                kotlin.runCatching {
                    state.value = State.Success
                    it.forEach { item ->
                        historyRepository.insertPhoto(item)
                    }
                }
                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + FIRST_PAGE
                )
            },
            onFailure = {
                Toast.makeText(
                    App.context,
                    R.string.error_load_db,
                    Toast.LENGTH_SHORT
                )
                    .show()
                val tempList = historyRepository.getHistoryPhoto(page)
                delay(5000)
                if (tempList.isEmpty()) {
                    Toast.makeText(
                        App.context,
                        "Данные в БД отсутствуют",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
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