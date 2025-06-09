package com.leo.weather.home.data.service

import com.leo.weather.home.data.model.WeatherInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") apiKey: String = "2e90b637f4e84c10ab600014250906",
        @Query("q") location: String,
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("tp") tp: String = "60"
    ): WeatherInfoResponse
}