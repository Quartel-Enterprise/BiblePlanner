package com.quare.bibleplanner.core.plan.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDataDto(
    @SerialName("number") val number: Int,
    @SerialName("verses") val verses: Int,
)
