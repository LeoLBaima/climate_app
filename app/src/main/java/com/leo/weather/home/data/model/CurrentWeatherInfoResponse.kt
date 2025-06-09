package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.CurrentWeatherInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherInfoResponse(
    @SerialName("temp_c")
    val temperature: Double,
    val condition: ConditionResponse,
) {
    fun toDomain() : CurrentWeatherInfo = CurrentWeatherInfo(
        temperature = temperature,
        condition = condition.toDomain(),
    )
}
