package com.example.clean.data

import com.example.clean.entity.UsefulActivity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import kotlin.concurrent.thread

private const val BASE_URL = "https://www.boredapi.com"


class UsefulActivitiesRepository @Inject constructor() {

    lateinit var usefulActivityDto: UsefulActivityDto

    fun getUsefulActivity(): UsefulActivity {
        val thread = thread {
            usefulActivityDto =
                RetrofitInstance.uploadingUsefulActivities.getUsefulActivities().execute().body()
                    ?: return@thread
        }

        while (thread.isAlive) {

        }
        return usefulActivityDto
    }

    object RetrofitInstance {
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        var uploadingUsefulActivities: UploadingUsefulActivities =
            retrofit.create(UploadingUsefulActivities::class.java)
    }

    interface UploadingUsefulActivities {
        @GET("/api/activity")
        fun getUsefulActivities(): Call<UsefulActivityDto>
    }
}
