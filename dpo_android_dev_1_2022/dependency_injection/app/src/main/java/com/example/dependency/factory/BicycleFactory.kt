package com.example.dependency.factory

import com.example.dependency.model.Bicycle
import com.example.dependency.model.WheelDealer

class BicycleFactory(private val frameFactory: FrameFactory, private val wheelDealer: WheelDealer) {
    private var frameSerialNumber = 1

    fun build(logo: String, color: Int): Bicycle {
        val frame = frameFactory.getFrame(frameSerialNumber++, color)
        val wheel1 = wheelDealer.getWheel()
        val wheel2 = wheelDealer.getWheel()
        return Bicycle(logo, frame, listOf(wheel1, wheel2))
    }
}