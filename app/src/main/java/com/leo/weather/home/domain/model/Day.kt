package com.leo.weather.home.domain.model

data class Day(
    val maxTemp: Double,
    val minTemp: Double,
    val condition: Condition,
)
