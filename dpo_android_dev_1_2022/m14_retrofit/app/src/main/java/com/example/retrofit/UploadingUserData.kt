package com.example.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://randomuser.me"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val uploadingUserData: UploadingUserData = retrofit.create(UploadingUserData::class.java)
}

interface UploadingUserData {
    @GET("/api")
    fun getUserDate(): Call<User>
}