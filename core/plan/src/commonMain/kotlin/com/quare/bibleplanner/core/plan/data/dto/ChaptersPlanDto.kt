package com.quare.bibleplanner.core.plan.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChaptersPlanDto(
    @SerialName("end") val end: EndChapterDto,
    @SerialName("start") val start: StartChapterPlanDto,
)
