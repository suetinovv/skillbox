package com.example.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.example.countdowntimer.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val KEY_COUNTER = "counter"
    private val KEY_TIME = "time"
    private val KEY_BUTTON_TEXT = "button"

    private lateinit var scope: CoroutineScope
    private var time = 10
    private var counter = 0

    private fun stopTimer() {
        scope.cancel()
        counter = 0
        binding.button.text = getText(R.string.start)
        binding.seekBar.isEnabled = true
        binding.textCounter.text = time.toString()
        binding.progressBar.progress = 0
    }

    private fun startTimer() {
        binding.button.text = getText(R.string.stop)
        binding.seekBar.isEnabled = false
        scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            while (counter < time) {
                delay(1000)
                counter++
                binding.textCounter.text = (time - counter).toString()
                binding.progressBar.progress = time - counter
            }
            Toast.makeText(
                this@MainActivity,
                R.string.time_finished,
                Toast.LENGTH_SHORT
            ).show()
            stopTimer()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(KEY_COUNTER)
            time = savedInstanceState.getInt(KEY_TIME)
                if (savedInstanceState.getString(KEY_BUTTON_TEXT) == getString(R.string.stop)) {
                    startTimer()
                }
        }
        binding.textCounter.text = (time - counter).toString()
        binding.progressBar.max = time

        binding.button.setOnClickListener {
            if (binding.button.text == getText(R.string.start)) {
                startTimer()
            } else {
                stopTimer()
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                time = seek.progress * 10 + 10
                binding.textCounter.text = (time - counter).toString()
                binding.progressBar.max = time
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_BUTTON_TEXT, binding.button.text.toString())
        outState.putInt(KEY_COUNTER, counter)
        outState.putInt(KEY_TIME, time)
        super.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        if (binding.button.text == getString(R.string.stop)) {
            scope.cancel()
        }
        super.onDestroy()
    }

}

