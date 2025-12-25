package com.quare.bibleplanner.core.utils.date

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

class LocalDateTimeProvider {
    fun getLocalDateTime(timestamp: Long): LocalDateTime = Instant
        .fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}
