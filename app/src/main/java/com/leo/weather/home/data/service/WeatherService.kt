package com.leo.weather.home.data.service

import com.leo.weather.home.data.model.WeatherInfoResponse
import retrofit2.http.GET

interface WeatherService {
    @GET("/forecast.json")
    fun getWeather(): WeatherInfoResponse
}