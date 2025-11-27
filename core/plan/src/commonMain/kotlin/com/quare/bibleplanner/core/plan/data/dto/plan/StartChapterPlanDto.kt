package com.quare.bibleplanner.core.plan.data.dto.plan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartChapterPlanDto(
    @SerialName("number") val number: Int,
    @SerialName("verse") val verse: Int,
)
