package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.Hour
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourResponse(
    @SerialName("temp_c")
    val temperature: Double,
    val condition: ConditionResponse
) {
    fun toDomain(): Hour = Hour(
        temperature = temperature,
        condition = condition.toDomain()
    )
}