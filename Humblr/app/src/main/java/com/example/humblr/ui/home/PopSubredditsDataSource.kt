package com.example.humblr.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.SubredditListItem
import javax.inject.Inject

class PopSubredditsDataSource @Inject constructor(
    private val repository: RemoteRepository,
) : PagingSource<String, SubredditListItem>() {

    override fun getRefreshKey(state: PagingState<String, SubredditListItem>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SubredditListItem> {
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            repository.getPopularSubreddits(page)

        }.fold(onSuccess = { response ->
            if (response.data.children.isEmpty()) {
                LoadResult.Page(
                    data = listOf(), prevKey = null, nextKey = null
                )
            } else {
                val subreddits = response.toSubredditList().onEach {
                    val isSubscribed = repository.getSubscription(it.id)
                    it.subscribed = isSubscribed
                }
                LoadResult.Page(
                    data = subreddits, prevKey = null, nextKey = response.data.after ?: ""
                )
            }
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    companion object {
        private const val FIRST_PAGE = ""
        const val PAGE_SIZE = 1
    }
}