package com.example.analogclock

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class TimeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val timeState = TimeState()
    private val startButton: Button
    private val resetButton: Button
    private val timeView: TextView
    private var timeListeners = mutableSetOf<(TimeState) -> Unit>()
    private lateinit var scope: CoroutineScope

    init {
        val root = inflate(context, R.layout.view_time, this)
        startButton = root.findViewById(R.id.button_start)
        resetButton = root.findViewById(R.id.button_reset)
        timeView = root.findViewById(R.id.time)
        timeView.text = convertToDate(currentTime().toString())

        startButton.setOnClickListener {
            if (!timeState.isPlayed) {
                start()
            } else {
                stop()
            }
        }
        resetButton.setOnClickListener { reset() }
    }

    private fun start() {
        startButton.text = "Стоп"
        timeState.isPlayed = true
        scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            while (timeState.isPlayed) {
                delay(1000)
                timeState.time++
                timeView.text = convertToDate(currentTime().toString())
            }
        }
    }

    private fun stop() {
        startButton.text = "Старт"
        timeState.isPlayed = false
        scope.cancel()
    }

    private fun reset() {
        stop()
        timeState.time = 0
        timeView.text = convertToDate(currentTime().toString())
    }

    private fun currentTime(): Long {
        return timeState.time
    }

    fun addUpdateListener(listener: (TimeState) -> Unit) {
        timeListeners.add(listener)
        listener(timeState)
    }

    fun removeUpdateListener(listener: (TimeState) -> Unit) {
        timeListeners.remove(listener)
    }

    private fun convertToDate(time: String?): String {
        time ?: return ""
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(time.toLong() * 1000))
    }

}