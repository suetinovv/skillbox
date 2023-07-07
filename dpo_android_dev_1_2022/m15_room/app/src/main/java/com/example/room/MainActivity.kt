package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.room.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {
            if (checkWord(getWord())) {
                viewModel.onButtonAdd(getWord())
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.error,
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
        binding.buttonClean.setOnClickListener { viewModel.onButtonClean() }

        lifecycleScope.launchWhenCreated {
            viewModel.fiveWords
                .collect {
                    binding.textView.text = it.joinToString(separator = "\r\n")

                }
        }


    }

    private fun getWord(): String {
        return binding.textInputLayout.editText?.text.toString()
    }

    private fun checkWord(word: String): Boolean {
        return ("([A-Za-zА-Яа-я]{2,15}-[A-Za-zА-Яа-я]{2,15})".toRegex().matches(word)
                || "[A-Za-zА-Яа-я]{2,15}".toRegex().matches(word))
    }
}