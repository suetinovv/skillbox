package com.example.humblr.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.humblr.data.remote.RemoteRepository
import com.example.humblr.domain.model.SubredditListItem
import com.example.humblr.domain.usecase.GetSubscribed
import com.example.humblr.domain.usecase.GetUnsubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSubscribed: GetSubscribed,
    private val getUnsubscribed: GetUnsubscribed,
    private val repository: RemoteRepository
) : ViewModel() {

    fun loadPagedNewSubreddits(): Flow<PagingData<SubredditListItem>> {
        val newListDataSource = NewSubredditsDataSource(repository)
        return Pager(config = PagingConfig(pageSize = NewSubredditsDataSource.PAGE_SIZE),
            pagingSourceFactory = { newListDataSource }).flow
    }

    fun loadPagedPopularSubreddits(): Flow<PagingData<SubredditListItem>> {
        val popListDataSource = PopSubredditsDataSource(repository)
        return Pager(config = PagingConfig(pageSize = PopSubredditsDataSource.PAGE_SIZE),
            pagingSourceFactory = { popListDataSource }).flow
    }

    fun loadPagedSearchSubreddits(query: String): Flow<PagingData<SubredditListItem>> {
        val searchListDataSource = SearchSubredditsDataSource(repository, query)
        return Pager(config = PagingConfig(pageSize = SearchSubredditsDataSource.PAGE_SIZE),
            pagingSourceFactory = { searchListDataSource }).flow
    }

    fun getUserSubscribed(subreddit: String?) {
        viewModelScope.launch() {
            getSubscribed(subreddit)
        }
    }

    fun getUserUnsubscribed(subreddit: String?) {
        viewModelScope.launch() {
            getUnsubscribed(subreddit)
        }
    }


}