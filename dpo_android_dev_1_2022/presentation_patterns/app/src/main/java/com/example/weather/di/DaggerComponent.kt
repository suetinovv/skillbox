package com.example.weather.di

import com.example.weather.App
import com.example.weather.api.WeatherApi
import com.example.weather.data.AppDatabase
import com.example.weather.data.HistoryDao
import com.example.weather.data.HistoryRepository
import com.example.weather.data.WeatherRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherModule::class])
interface DaggerComponent {
    fun getWeatherRepository(): WeatherRepository
    fun getHistoryRepository(): HistoryRepository
}

@Module
class WeatherModule {
    @Provides
    @Singleton
    fun provideHistoryDao(): HistoryDao {
        return App.db.historyDao()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApi.create()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ): WeatherRepository {
        return WeatherRepository(weatherApi)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        historyDao: HistoryDao
    ): HistoryRepository {
        return HistoryRepository(historyDao)
    }
}