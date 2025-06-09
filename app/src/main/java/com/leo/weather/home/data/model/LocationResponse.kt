package com.leo.weather.home.data.model

import com.leo.weather.home.domain.model.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val name: String,
    @SerialName("localtime")
    val localTime: String,
) {
    fun toDomain() : Location = Location(
        name = name,
        localTime = localTime,
    )
}
