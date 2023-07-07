package com.example.weather.api

import com.example.weather.BuildConfig
import com.example.weather.data.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("key") key: String = BuildConfig.WATHER_ACCESS_KEY
    ): Weather

    companion object {
        private const val BASE_URL = "https://api.weatherapi.com/"

        fun create(): WeatherApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}