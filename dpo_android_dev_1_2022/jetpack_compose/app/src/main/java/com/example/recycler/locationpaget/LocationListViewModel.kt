package com.example.recycler.locationpaget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recycler.State
import com.example.recycler.models.location.Location

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationListViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    val pagedLocations: Flow<PagingData<Location>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { LocationPadingSource(_state) }
    ).flow.cachedIn(viewModelScope)
}