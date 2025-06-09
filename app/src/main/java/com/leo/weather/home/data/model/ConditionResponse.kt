package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.Condition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConditionResponse(
    @SerialName("text")
    val condition: String,
) {
    fun toDomain() : Condition = Condition(condition = condition)
}