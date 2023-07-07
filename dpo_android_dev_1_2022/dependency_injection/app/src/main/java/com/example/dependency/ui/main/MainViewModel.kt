package com.example.dependency.ui.main


import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.dependency.factory.BicycleFactory
import com.example.dependency.model.Bicycle

class MainViewModel(private val bicycleFactory: BicycleFactory) : ViewModel() {

    fun createDagger(): Bicycle {
        return bicycleFactory.build("Dagger Logo", Color.RED)
    }

}