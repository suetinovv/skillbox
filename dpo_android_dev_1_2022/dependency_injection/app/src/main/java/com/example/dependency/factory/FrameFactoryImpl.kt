package com.example.dependency.factory

import com.example.dependency.model.Frame

class FrameFactoryImpl : FrameFactory {
    override fun getFrame(serialNumber: Int, color: Int): Frame {
        return Frame(serialNumber, color)
    }
}