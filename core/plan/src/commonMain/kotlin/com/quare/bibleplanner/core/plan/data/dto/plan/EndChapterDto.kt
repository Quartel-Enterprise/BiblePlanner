package com.quare.bibleplanner.core.plan.data.dto.plan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EndChapterDto(
    @SerialName("number") val number: Int,
    @SerialName("verse") val verse: Int,
)
