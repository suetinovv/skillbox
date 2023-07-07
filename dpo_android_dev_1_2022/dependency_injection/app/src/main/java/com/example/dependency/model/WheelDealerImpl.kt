package com.example.dependency.model

object WheelDealerImpl : WheelDealer {
    private var serialNumber = 1
    override fun getWheel(): Wheel {
        return Wheel(serialNumber++)
    }
}