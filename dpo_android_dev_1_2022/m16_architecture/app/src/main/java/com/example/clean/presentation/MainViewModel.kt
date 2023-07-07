package com.example.clean.presentation

import androidx.lifecycle.ViewModel
import com.example.clean.domain.GetUsefulActivityUseCase
import com.example.clean.entity.UsefulActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val getUsefulActivityUseCase: GetUsefulActivityUseCase) :
    ViewModel() {

    private val _text = MutableStateFlow<UsefulActivity>(getUsefulActivityUseCase.execute())
    val text = _text.asStateFlow()

    fun reloadUsefulActivity() {
        _text.value = getUsefulActivityUseCase.execute()
    }
}