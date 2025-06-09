package com.leo.weather.home.domain.model

data class WeatherInfo(
    val location: Location,
    val currentWeatherInfo: CurrentWeatherInfo,
    val forecast: Forecast,
)