package com.example.dependency.ui.main

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.dependency.factory.BicycleFactory
import com.example.dependency.model.Bicycle

class MainKoinViewModel(private val bicycleFactory: BicycleFactory) : ViewModel() {
    fun createKoin(): Bicycle {
        return bicycleFactory.build("Koin Logo", Color.GREEN)
    }
}