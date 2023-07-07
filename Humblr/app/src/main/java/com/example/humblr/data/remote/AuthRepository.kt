package com.example.humblr.data.remote

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.example.humblr.authorization.AuthConfig
import com.example.humblr.data.remote.models.TokenStorage
import com.example.humblr.data.remote.models.TokensModel
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.TokenRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class AuthRepository @Inject constructor(private val tokenStorage: TokenStorage) {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTH_URI),
        Uri.parse(AuthConfig.TOKEN_URI),
        null, // registration endpoint
        Uri.parse(AuthConfig.END_SESSION_URI)
    )

    private fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)

        return AuthorizationRequest.Builder(
            serviceConfiguration, AuthConfig.CLIENT_ID, AuthConfig.RESPONSE_TYPE, redirectUri
        ).setState(AuthConfig.STATE)
            .setAdditionalParameters(mapOf(Pair(DURATION, AuthConfig.DURATION)))
            .setScope(AuthConfig.SCOPE).build()
    }

    fun sendAuthRequest(authService: AuthorizationService): Intent {
        val authRequest = getAuthRequest()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        return authService.getAuthorizationRequestIntent(authRequest, customTabsIntent)
    }

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
                tokenStorage.accessToken = response.accessToken
                tokenStorage.refreshToken = response.refreshToken
                onSuccess()
            }
            if (exception != null) {
            }
        }
    }

    suspend fun performRefreshTokenSuspend(
        authService: AuthorizationService,
    ): TokensModel {
        val clientAuth: ClientAuthentication = ClientSecretBasic(AuthConfig.CLIENT_SECRET)
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                TokenRequest.Builder(serviceConfiguration, AuthConfig.CLIENT_ID)
                    .setGrantType(GrantTypeValues.REFRESH_TOKEN)
                    .setRefreshToken(tokenStorage.refreshToken).build(), clientAuth
            ) { response, ex ->
                when {
                    response != null -> {
                        tokenStorage.accessToken = response.accessToken.orEmpty()
                        tokenStorage.refreshToken = response.refreshToken.orEmpty()
                        tokenStorage.idToken = response.idToken.orEmpty()
                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))
                    }

                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                    }

                    else -> error("unreachable")
                }
            }
        }
    }

    companion object {
        const val DURATION = "duration"
    }
}
