package com.example.practice.api

import com.example.practice.data.AccessToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UnsplashOAuthApi {

    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String,
        @Field("grant_type") grantType: String
    ): AccessToken

    companion object {
        private const val BASE_URL = "https://unsplash.com/"

        fun create(): UnsplashOAuthApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                        })
                        .build()
                )
                .build()
                .create(UnsplashOAuthApi::class.java)

        }
    }
}