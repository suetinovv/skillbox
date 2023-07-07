package com.example.analogclock

class TimeState {
    var time: Long = 0
        set(value) {
            field = if (value != (60 * 60 * 12).toLong()) {
                value
            } else {
                0
            }
        }

    var isPlayed = false
}