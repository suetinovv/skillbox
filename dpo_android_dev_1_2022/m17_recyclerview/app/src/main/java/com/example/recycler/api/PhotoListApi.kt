package com.example.recycler.api

import com.example.recycler.models.PhotoList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov"
private const val API_KEY = "MmnUjOTqfyf4KtExhCNvcMzEhcCZXUwzjBHfFxrO"

interface PhotoListApi {

    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&api_key=$API_KEY")
    suspend fun photos(): PhotoList

}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(PhotoListApi::class.java)
