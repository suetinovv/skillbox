package com.example.practice.di

import com.example.practice.App
import com.example.practice.api.UnsplashApi
import com.example.practice.api.UnsplashOAuthApi
import com.example.practice.data.HistoryRepository
import com.example.practice.data.UnsplashOAuthRepository
import com.example.practice.data.UnsplashRepository
import com.example.practice.data.photo.HistoryPhotoDao
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [UnsplashModule::class])
interface DaggerComponent {
    fun getUnsplashRepository(): UnsplashRepository
    fun getUnsplashOAuthRepository(): UnsplashOAuthRepository
    fun getHistoryRepository(): HistoryRepository
}

@Module
class UnsplashModule {
    @Provides
    @Singleton
    fun provideHistoryPhotoDao(): HistoryPhotoDao {
        return App.db.historyPhotoDao()
    }

    @Provides
    @Singleton
    fun provideUnsplashApi(): UnsplashApi {
        return UnsplashApi.create(App.accessToken)
    }

    @Provides
    @Singleton
    fun provideUnsplashRepository(
        unsplashApi: UnsplashApi
    ): UnsplashRepository {
        return UnsplashRepository(unsplashApi)
    }

    @Provides
    @Singleton
    fun provideUnsplashOAuthApi(): UnsplashOAuthApi {
        return UnsplashOAuthApi.create()
    }

    @Provides
    @Singleton
    fun provideUnsplashOAuthRepository(
        unsplashOAuthApi: UnsplashOAuthApi
    ): UnsplashOAuthRepository {
        return UnsplashOAuthRepository(unsplashOAuthApi)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        historyPhotoDao: HistoryPhotoDao
    ): HistoryRepository {
        return HistoryRepository(App.context, historyPhotoDao)
    }
}