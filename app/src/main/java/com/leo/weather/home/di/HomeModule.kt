package com.leo.weather.home.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.leo.weather.home.data.repository.WeatherRepositoryImpl
import com.leo.weather.home.data.service.WeatherService
import com.leo.weather.home.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    fun providesWeatherService(): WeatherService {
        val contentType = MediaType.parse("application/json")
        val json = Json {
            ignoreUnknownKeys = true
        }
        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType!!))
            .baseUrl("https://api.weatherapi.com/v1/")
            .build()

        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    fun providesWeatherRepository(
        service: WeatherService
    ): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}