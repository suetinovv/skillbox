package com.example.location.data


import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.concurrent.thread

private const val BASE_URL = "https://api.opentripmap.com"
private const val API_KEY = "5ae2e3f221c38a28845f05b64c25979afc2e469540ea948bbbee9ca3"

class AttractionRepository @Inject constructor() {

    lateinit var attractions: List<AttractionsItem>

    fun getAttractions(lon: Double, lat: Double): List<AttractionsItem> {
        val thread = thread {
            if (RetrofitInstance.uploadingAttractions.getAttractions(lon =lon, lat = lat).execute().isSuccessful) {
                attractions =
                    RetrofitInstance.uploadingAttractions.getAttractions(lon =lon, lat = lat).execute().body()!!

            }
        }

        while (thread.isAlive) {

        }
        return attractions
    }


    object RetrofitInstance {
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


        val uploadingAttractions: UploadingAttractions =
            retrofit.create(UploadingAttractions::class.java)


    }


    interface UploadingAttractions {
        @GET("/0.1/ru/places/radius")
        fun getAttractions(
            @Query("lon")lon: Double,
            @Query("lat")lat: Double,
            @Query("radius")radius: Int = 200000,
            @Query("format")format: String = "json",
            @Query("apikey")apikey: String = API_KEY
        ): Call<List<AttractionsItem>>
    }
}