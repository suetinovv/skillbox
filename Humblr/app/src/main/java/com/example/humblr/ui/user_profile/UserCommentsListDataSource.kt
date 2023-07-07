package com.example.humblr.ui.user_profile

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.CommentListItem
import javax.inject.Inject

class UserCommentsListDataSource @Inject constructor(
    private val repository: RemoteRepository, private val userName: String
) : PagingSource<String, CommentListItem>() {

    override fun getRefreshKey(state: PagingState<String, CommentListItem>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommentListItem> {
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            repository.getUserComments(userName, page)

        }.fold(onSuccess = { response ->
            if (response.data?.children?.size!! < PAGE_SIZE) {
                LoadResult.Page(
                    data = listOf(), prevKey = null, nextKey = null
                )
            } else {
                val comments = response.toUserComments()
                LoadResult.Page(
                    data = comments, prevKey = null, nextKey = response.data?.after ?: ""
                )
            }
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    companion object {
        private const val FIRST_PAGE = ""
        const val PAGE_SIZE = 50
    }
}
