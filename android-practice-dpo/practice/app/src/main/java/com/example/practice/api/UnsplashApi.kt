package com.example.practice.api

import com.example.practice.data.collections.CollectionInfo
import com.example.practice.data.collections.CollectionsItem
import com.example.practice.data.foto_like.PhotoLike
import com.example.practice.data.user.UserInfo
import com.example.practice.data.photo.PhotoItem
import com.example.practice.data.photo_info.PhotoInfo
import com.example.practice.data.search_photo.SearchPhoto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("/me")
    suspend fun getUserInfo(): UserInfo

    @GET("/photos")
    suspend fun getListPhoto(@Query("page") page: Int): List<PhotoItem>

    @GET("/users/{userName}/photos")
    suspend fun getListUserPhoto(@Path("userName") userName: String, @Query("page") page: Int): List<PhotoItem>

    @GET("/users/{userName}/likes")
    suspend fun getListUserLikesPhoto(@Path("userName") userName: String, @Query("page") page: Int): List<PhotoItem>
    @GET("/users/{userName}/collections")
    suspend fun getListUserCollections(@Path("userName") userName: String, @Query("page") page: Int): List<CollectionsItem>

    @GET("/search/photos")
    suspend fun getSearchListPhoto(
        @Query("page") page: Int,
        @Query("query") query: String
    ): SearchPhoto

    @GET("/collections")
    suspend fun getListCollections(@Query("page") page: Int): List<CollectionsItem>

    @GET("/photos/{id}")
    suspend fun getPhoto(@Path("id") id: String): PhotoInfo

    @GET("/collections/{id}")
    suspend fun getCollectionInfo(@Path("id") id: String): CollectionInfo

    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhoto(
        @Path("id") id: String,
        @Query("page") page: Int
    ): List<PhotoItem>

    @POST("/photos/{id}/like")
    suspend fun setLike(@Path("id") id: String): PhotoLike

    @DELETE("/photos/{id}/like")
    suspend fun unlike(@Path("id") id: String): PhotoLike

    companion object {
        private const val BASE_URL = "https://api.unsplash.com"

        fun create(accessToken: String): UnsplashApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer $accessToken"
                        )
                        .build()
                    chain.proceed(request)
                }.build())
                .build()
                .create(UnsplashApi::class.java)
        }
    }
}