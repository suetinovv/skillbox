package com.example.recycler.paged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.recycler.State
import com.example.recycler.models.Personage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class PersonagePagingListViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    val pagedPersonages: Flow<PagingData<Personage>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PersonagePagingSource(_state) }
    ).flow.cachedIn(viewModelScope)

}