package com.example.practice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.practice.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var getAuthResponse: ActivityResultLauncher<Intent>
    private val authViewModel: AuthViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(
                    application,
                    App.component.getUnsplashOAuthRepository()
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("is_first_run", true)
        val accessToken = prefs.getString("access_token", "")

        if (isFirstRun) {
            startActivity(Intent(this, OnboardingActivity::class.java))
        } else {
            if (accessToken != "") {
                startActivity(
                    Intent(
                        applicationContext,
                        MainActivity::class.java
                    )
                )
            }
            binding = ActivityAuthBinding.inflate(layoutInflater)
            setContentView(binding.root)

            getAuthResponse =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    val dataIntent = it.data ?: return@registerForActivityResult
                    if (Uri.parse(dataIntent.toString()).getQueryParameter("error") == null) {
                        if (it.resultCode == RESULT_OK) {
                            logIn(it.data!!)
                            Toast.makeText(
                                applicationContext, getString(R.string.success), Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (it.resultCode == RESULT_CANCELED) {
                            Toast.makeText(
                                applicationContext, getString(R.string.failure), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            lifecycleScope.launchWhenStarted {
                authViewModel.isAuthSuccess.collect { success ->
                    if (success) {
                        startActivity(
                            Intent(
                                applicationContext,
                                MainActivity::class.java
                            )
                        )
                    }
                }
            }

            binding.buttonLogin.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    if (accessToken == "") {
                        logIn(null)
                    } else {
                        startActivity(
                            Intent(
                                applicationContext,
                                MainActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }

    private fun logIn(dataIntent: Intent?) {
        if (dataIntent == null) {
            val authIntent = authViewModel.startAuthIntent()
            getAuthResponse.launch(authIntent)
        } else {
            val code = Uri.parse(dataIntent.data.toString()).getQueryParameter("code")
            code?.let { authViewModel.requestToken(it) }
        }
    }
}
