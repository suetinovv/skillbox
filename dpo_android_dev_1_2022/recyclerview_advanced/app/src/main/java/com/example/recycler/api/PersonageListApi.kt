package com.example.recycler.api

import com.example.recycler.models.PersonageList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://rickandmortyapi.com"

interface PersonageListApi {

    @GET("/api/character")
    suspend fun personageList(@Query("pdatee") page: Int): PersonageList

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
    .create(PersonageListApi::class.java)
