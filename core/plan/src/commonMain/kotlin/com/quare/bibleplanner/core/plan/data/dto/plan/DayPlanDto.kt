package com.quare.bibleplanner.core.plan.data.dto.plan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayPlanDto(
    @SerialName("books") val books: List<BookPlanDto>,
    @SerialName("day") val day: Int,
)
