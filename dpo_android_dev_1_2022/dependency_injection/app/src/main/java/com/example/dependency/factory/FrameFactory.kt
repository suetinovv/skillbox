package com.example.dependency.factory

import com.example.dependency.model.Frame

interface FrameFactory {
    fun getFrame(serialNumber: Int, color: Int): Frame
}