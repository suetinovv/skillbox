package com.example.practice

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.data.UnsplashOAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationService


class AuthViewModel(
    private val application: Application,
    private val unsplashOAuthRepository: UnsplashOAuthRepository
) : AndroidViewModel(application) {

    private val _isAuthSuccess = MutableStateFlow(false)
    val isAuthSuccess = _isAuthSuccess.asStateFlow()
    private val authService: AuthorizationService = AuthorizationService(getApplication())

    fun startAuthIntent(): Intent {
        return unsplashOAuthRepository.sendAuthRequest(authService)
    }

    fun requestToken(code: String) {
        unsplashOAuthRepository.performRequestToken(code, authService) {
            _isAuthSuccess.value = true
        }
    }

    fun logout() {
        viewModelScope.launch {
            val intent = Intent(application.applicationContext, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            application.applicationContext.startActivity(intent)
        }
    }

}