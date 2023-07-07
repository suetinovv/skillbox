package com.example.humblr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.humblr.databinding.ActivityOnBoardingBinding
import com.example.humblr.domain.model.NavArgs
import com.example.humblr.ui.onboarding.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : FragmentActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 2) {
                        binding.button.text = "Готово"
                    } else {
                        binding.button.text = "Пропустить"
                    }
                }
            }
        )

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()

        binding.button.setOnClickListener {
            val sharedPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
            sharedPrefs.edit().putBoolean(NavArgs.ONBOARDED.key, false).apply()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}