package com.example.recycler

sealed class State {
    data class Error(var throwable: Throwable) : State()
    object Loading : State()
    object Success : State()

}
