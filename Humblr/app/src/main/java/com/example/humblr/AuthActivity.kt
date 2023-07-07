package com.example.humblr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.humblr.data.remote.models.TokenStorage
import com.example.humblr.databinding.ActivityAuthBinding
import com.example.humblr.domain.model.NavArgs
import com.example.humblr.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var getAuthResponse: ActivityResultLauncher<Intent>
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE) ?: return
        val isFirstRun = sharedPrefs.getBoolean(NavArgs.ONBOARDED.key, true)
        TokenStorage.accessToken = sharedPrefs.getString(ACCESS_TOKEN, null)
        TokenStorage.refreshToken = sharedPrefs.getString(REFRESH_TOKEN, null)

        if (isFirstRun) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        } else {
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

            binding.loginButton.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    if (TokenStorage.refreshToken == null) {
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

    override fun onStop() {
        super.onStop()
        val sharedPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE) ?: return
        with(sharedPrefs.edit()) {
            putString(ACCESS_TOKEN, TokenStorage.accessToken)
            putString(REFRESH_TOKEN, TokenStorage.refreshToken)
            apply()
        }
    }

    companion object {
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
    }
}