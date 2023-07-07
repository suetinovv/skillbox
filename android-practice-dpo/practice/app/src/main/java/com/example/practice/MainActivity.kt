package com.example.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_photo, R.id.navigation_collections, R.id.navigation_user,
                R.id.navigation_photo_info
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_photo -> {
                    binding.toolbarButton.setImageResource(R.drawable.baseline_search_24)
                }

                R.id.navigation_user -> {
                    binding.toolbarButton.setImageResource(R.drawable.baseline_logout_24)
                }

                R.id.navigation_photo_info -> {
                    binding.toolbarButton.setImageResource(R.drawable.baseline_share_24)
                }
            }
        }

    }
}
