package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.WeatherInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfoResponse(
    val location: LocationResponse,
    @SerialName("current")
    val currentWeatherInfo: CurrentWeatherInfoResponse,
    val forecast: ForecastResponse,
) {
    fun toDomain() : WeatherInfo = WeatherInfo(
        location = location.toDomain(),
        currentWeatherInfo = currentWeatherInfo.toDomain(),
        forecast = forecast.toDomain(),
    )
}