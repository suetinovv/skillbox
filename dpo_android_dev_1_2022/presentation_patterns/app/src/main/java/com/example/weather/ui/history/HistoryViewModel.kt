package com.example.weather.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.History
import com.example.weather.data.HistoryDao
import com.example.weather.data.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repositoryHistory: HistoryRepository) : ViewModel() {

    private val _listHistory = MutableStateFlow<List<History>>(emptyList())
    val listHistory = _listHistory.asStateFlow()

    fun loadListCities(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repositoryHistory.getHistory(city)
            }.fold(
                onSuccess = {
                    _listHistory.value = it
                },
                onFailure = {
                }
            )
        }
    }
}