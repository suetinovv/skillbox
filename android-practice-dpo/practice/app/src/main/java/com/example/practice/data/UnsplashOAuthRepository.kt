package com.example.practice.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.example.practice.App
import com.example.practice.api.UnsplashOAuthApi
import com.example.practice.authorization.AuthConfig
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.TokenRequest

class UnsplashOAuthRepository(private val unsplashOAuthApi: UnsplashOAuthApi) {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTH_URI),
        Uri.parse(AuthConfig.TOKEN_URI),
        null, // registration endpoint
        Uri.parse(AuthConfig.END_SESSION_URI)
    )
//    suspend fun getAccessToken(code: String): AccessToken {
//        return unsplashOAuthApi.getAccessToken(
//            clientId = CLIENT_ID,
//            clientSecret = CLIENT_SECRET,
//            redirectUri = REDIRECT_URI,
//            code = code,
//            grantType = "authorization_code"
//        )
//    }
    fun performRequestToken(
        code: String, authService: AuthorizationService, onSuccess: () -> Unit
    ) {
        val clientAuth: ClientAuthentication = ClientSecretBasic(AuthConfig.CLIENT_SECRET)
        authService.performTokenRequest(
            TokenRequest.Builder(serviceConfiguration, AuthConfig.CLIENT_ID)
                .setAuthorizationCode(code)
                .setRedirectUri(AuthConfig.CALLBACK_URL.toUri())
                .setGrantType(GrantTypeValues.AUTHORIZATION_CODE)
                .build(), clientAuth
        ) { response, exception ->
            if (response != null) {
                val prefs = App.context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                prefs.edit().putString("access_token", response.accessToken).apply()
                App.accessToken = response.accessToken?: ""
                onSuccess()
            }
            if (exception != null) {
                Log.d("mytest", exception.message?: "Ошибка получения токена")
            }
        }
    }
    fun sendAuthRequest(authService: AuthorizationService): Intent {
        val authRequest = getAuthRequest()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        return authService.getAuthorizationRequestIntent(authRequest, customTabsIntent)
    }

    private fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)

        return AuthorizationRequest.Builder(
            serviceConfiguration, AuthConfig.CLIENT_ID, AuthConfig.RESPONSE_TYPE, redirectUri
        ).setScope(AuthConfig.SCOPE).build()
    }

}