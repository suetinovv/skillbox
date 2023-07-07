package com.example.weather.ui.history

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.weather.data.History
import com.example.weather.databinding.ItemHistoryBinding

class HistoryItem
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ItemHistoryBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setInfo(history: History) {
        binding.textTempC.text = history.temp_c
        binding.textDate.text = history.localtime
        binding.text.text = history.text
    }

}
