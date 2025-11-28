package com.quare.bibleplanner.core.plan.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeekPlanDto(
    @SerialName("days") val days: List<DayPlanDto>,
    @SerialName("week") val week: Int,
)
