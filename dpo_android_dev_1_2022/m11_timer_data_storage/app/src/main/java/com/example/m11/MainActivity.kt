package com.example.m11

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.m11.databinding.ActivityMainBinding

private const val PREFERENCE_NAME = "prefs_name"

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(this)

        binding.textView.text = repository.getText()

        prefs = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)

        binding.buttonSave.setOnClickListener {
            repository.saveText(binding.editText.text.toString())
            binding.textView.text = repository.getText()
        }

        binding.buttonClean.setOnClickListener {
            repository.clearText()
            binding.textView.text = repository.getText()
        }
    }

}