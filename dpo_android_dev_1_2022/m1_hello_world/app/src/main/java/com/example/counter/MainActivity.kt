package com.example.counter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMinus.setOnClickListener {
            counter--
            binding.counterText.text = counter.toString()
            if (counter == 0) {
                binding.centerText.text = binding.centerText.context.getText(R.string.start_text)
                binding.centerText.setTextColor(Color.GREEN)
                binding.buttonMinus.isEnabled = false
            } else {
                binding.centerText.text = "Осталось мест: " + (50 - counter)
                binding.centerText.setTextColor(Color.BLUE)
                binding.buttonPlus.isEnabled = true
                binding.buttonReset.visibility = View.INVISIBLE
            }
        }

        binding.buttonPlus.setOnClickListener {
            counter++
            binding.counterText.text = counter.toString()
            if (counter == 50) {
                binding.centerText.text = "Пассажиров слишком много!"
                binding.centerText.setTextColor(Color.RED)
                binding.buttonPlus.isEnabled = false
                binding.buttonReset.visibility = View.VISIBLE
            } else {
                binding.centerText.text = "Осталось мест: " + (50 - counter)
                binding.centerText.setTextColor(Color.BLUE)
                binding.buttonMinus.isEnabled = true
            }
        }

        binding.buttonReset.setOnClickListener {
            counter = 0
            binding.counterText.text = counter.toString()
            binding.centerText.text = binding.centerText.context.getText(R.string.start_text)
            binding.centerText.setTextColor(Color.GREEN)
            binding.buttonMinus.isEnabled = false
            binding.buttonReset.visibility = View.INVISIBLE
            binding.buttonPlus.isEnabled = true
        }

    }
}