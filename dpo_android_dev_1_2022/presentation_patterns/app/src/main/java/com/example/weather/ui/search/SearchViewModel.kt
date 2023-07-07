package com.example.weather.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.State
import com.example.weather.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel(
    private val repositoryWeather: WeatherRepository,
    private val repositoryHistory: HistoryRepository
) : ViewModel() {

    private val _text = MutableStateFlow<List<ResultWeather>>(emptyList())
    val text = _text.asStateFlow()

    private val _state = MutableStateFlow<State>(State.Empty)
    val state = _state.asStateFlow()

    fun loadWeather(q: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _state.value = State.Loading
                repositoryWeather.getWeather(q)
            }.fold(
                onSuccess = {
                    _text.value = listOf(it)
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    val currentDate: String = sdf.format(Date())
                    repositoryHistory.insert(
                        History(
                            city_and_date = "${it.location.name}_${currentDate}",
                            name_city = it.location.name,
                            date = currentDate,
                            temp_c = it.current.temp_c.toString(),
                            text = it.current.condition.text,
                            localtime = it.location.localtime
                        )
                    )
                    _state.value = State.SuccessWeather
                },
                onFailure = {
                    loadHistory(q)
                }
            )
        }
    }

    private fun loadHistory(q: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repositoryHistory.getHistory(q)
            }.fold(
                onSuccess = {
                    if (it.isNotEmpty()) {
                        _text.value = it
                        _state.value = State.SuccessHistory
                    } else {
                        _state.value = State.Error(Throwable())
                    }
                },
                onFailure = {
                    _state.value = State.Error(it)
                }
            )
        }
    }
}
