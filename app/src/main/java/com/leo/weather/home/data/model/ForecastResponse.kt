package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.Forecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("forecastday")
    val forecastDays: List<ForecastDayResponse>,
) {
    fun toDomain(): Forecast = Forecast(
        forecastDays = forecastDays.map {
            it.toDomain()
        }
    )
}
