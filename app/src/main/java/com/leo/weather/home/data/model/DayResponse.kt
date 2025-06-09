package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.Day
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayResponse(
    @SerialName("maxtemp_c") val maxTemp: Double,
    @SerialName("mintemp_c") val minTemp: Double,
    val condition: ConditionResponse,
) {
    fun toDomain(): Day =
        Day(maxTemp = maxTemp, minTemp = minTemp, condition = condition.toDomain())
}
