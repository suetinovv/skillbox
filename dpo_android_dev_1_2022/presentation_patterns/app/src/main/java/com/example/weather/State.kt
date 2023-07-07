package com.example.weather

sealed class State {
    data class Error(var throwable: Throwable) : State()
    object Loading : State()
    object SuccessHistory : State()
    object SuccessWeather : State()
    object Empty : State()
}