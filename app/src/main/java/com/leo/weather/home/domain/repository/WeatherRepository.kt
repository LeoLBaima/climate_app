package com.leo.weather.home.domain.repository

import com.leo.weather.home.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeather(location: String) : WeatherInfo
}