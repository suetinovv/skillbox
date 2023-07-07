package com.example.mvvm.ui.main

sealed class State {
    data class Result(var text: String) : State()
    object NoActivButton : State()
    object ActivButton : State()
    object Loading : State()
}