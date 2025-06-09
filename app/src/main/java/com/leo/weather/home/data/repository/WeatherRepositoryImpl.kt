package com.leo.weather.home.data.repository

import com.leo.weather.home.data.service.WeatherService
import com.leo.weather.home.domain.model.WeatherInfo
import com.leo.weather.home.domain.repository.WeatherRepository
import retrofit2.http.GET
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService) :
    WeatherRepository {

    override fun getWeather(): WeatherInfo = service.getWeather().toDomain()
}