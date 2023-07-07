package com.example.skillbox_hw_quiz

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import com.example.skillbox_hw_quiz.databinding.ActivityQuestBinding
import com.example.skillbox_hw_quiz.quiz.Question

class QuestView
@JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ActivityQuestBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun addRadioButton(question: Question) {
        for ((count, i) in question.answers.withIndex()) {
            val button = RadioButton(context)
            button.text = i
            button.id = count
            binding.radio.addView(button)
        }

    }

    fun setTextQuest(text: String) {
        binding.textQuest.text = text
    }

    fun getTextQuest(): String {
        return binding.textQuest.text.toString()
    }

    fun getFeedBack(): Int {
        println(binding.radio.checkedRadioButtonId)
        return  binding.radio.checkedRadioButtonId
    }


}