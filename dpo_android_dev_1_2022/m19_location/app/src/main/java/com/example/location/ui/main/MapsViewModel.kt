package com.example.location.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.location.data.AttractionsItem
import com.example.location.domain.GetAttractionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getAttractionsUseCase: GetAttractionsUseCase
) : ViewModel() {

    private val _attraction = MutableStateFlow<List<AttractionsItem>>(emptyList())
    val attraction = _attraction.asStateFlow()

    fun loadInfo(lon: Double, lat: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getAttractionsUseCase.execute(lon, lat)
            }.fold(
                onSuccess = {
                    _attraction.value = it
                },
                onFailure = {
                    Log.d("Attractions", it.message ?: "")
                    loadInfo(lon, lat)
                }
            )
        }
    }
}