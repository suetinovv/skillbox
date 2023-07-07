package com.example.practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.practice.databinding.ActivityOnboardingBinding
import com.example.practice.ui.onboarding.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 2) {
                        binding.button.visibility = View.VISIBLE
                    } else {
                        binding.button.visibility = View.INVISIBLE
                    }
                }
            }
        )

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()

        binding.button.setOnClickListener {
            val prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
            prefs.edit().putBoolean("is_first_run", false).apply()
            startActivity(Intent(this, AuthActivity::class.java))
        }
        binding.lottieView.setOnClickListener {
            binding.lottieView.visibility = View.GONE
        }
    }
}