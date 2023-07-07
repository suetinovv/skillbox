package com.example.weather.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.State
import com.example.weather.data.HistoryDao
import com.example.weather.data.HistoryRepository
import com.example.weather.data.ResultWeather
import com.example.weather.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CitiesViewModel(private val repositoryHistory: HistoryRepository) : ViewModel() {

    private val _list = MutableStateFlow<List<String>>(emptyList())
    val list = _list.asStateFlow()

    fun loadListCities() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repositoryHistory.getAllCities()
            }.fold(
                onSuccess = {
                    _list.value = it
                },
                onFailure = {

                }
            )
        }
    }
}