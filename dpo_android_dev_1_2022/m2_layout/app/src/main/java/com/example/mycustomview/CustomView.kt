package com.example.mycustomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.mycustomview.databinding.MyCustomLayoutBinding


class CustomView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = MyCustomLayoutBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setUpText(text: String){
        binding.upStr.text = text
    }
    fun setDownText(text: String){
        binding.downStr.text = text
    }
}