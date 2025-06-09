package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.ForecastDay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayResponse(
    val date: String,
    val day: DayResponse,
    @SerialName("hour")
    val hoursInterval: List<HourResponse>
) {
    fun toDomain(): ForecastDay = ForecastDay(
        date = date,
        day = day.toDomain(),
        hoursInterval = hoursInterval.map {
            it.toDomain()
        }
    )
}
