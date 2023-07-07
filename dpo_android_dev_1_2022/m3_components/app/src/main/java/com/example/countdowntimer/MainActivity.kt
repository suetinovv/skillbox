package com.example.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.example.countdowntimer.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var scope: CoroutineScope
    var time = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textCounter.text = time.toString()

        fun stopTimer(){
            scope.cancel()
            binding.button.text = getText(R.string.start)
            binding.seekBar.isEnabled = true
            binding.textCounter.text = time.toString()
            binding.progressBar.progress = 0
        }

        fun startTimer() {
            binding.button.text = getText(R.string.stop)
            binding.seekBar.isEnabled = false
            scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                var counter = 0
                while (counter < time) {
                    delay(1000)
                    counter++
                    binding.textCounter.text = (time - counter).toString()
                    binding.progressBar.progress = counter
                }
                Toast.makeText(this@MainActivity,
                    R.string.time_finished,
                    Toast.LENGTH_SHORT).show()
                stopTimer()
            }
        }

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
                binding.textCounter.text =  time.toString()
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

}

