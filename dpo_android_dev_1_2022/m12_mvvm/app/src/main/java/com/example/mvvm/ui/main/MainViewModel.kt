package com.example.mvvm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.NoActivButton)
    val state = _state.asStateFlow()

    fun setStatusButton(count: Int) {
        if (state.value != State.Loading) {
            if (count >= 3) {
                _state.value = State.ActivButton
            } else {
                _state.value = State.NoActivButton
            }
        }
    }

    fun getTextSearch(text: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            delay(7000)
            _state.value = State.Result("По запросу <$text> ничего не найдено")
        }
    }


}