package com.quare.bibleplanner.feature.day.domain.repository

import com.quare.bibleplanner.core.model.plan.DayModel
import kotlinx.coroutines.flow.Flow

interface DayRepository {
    fun getDayByWeekAndDayFlow(
        weekNumber: Int,
        dayNumber: Int,
    ): Flow<DayModel?>

    suspend fun getDayByWeekAndDay(
        weekNumber: Int,
        dayNumber: Int,
    ): DayModel?

    suspend fun updateDayReadStatus(
        weekNumber: Int,
        dayNumber: Int,
        isRead: Boolean,
        readTimestamp: Long?,
    )

    suspend fun updatePassageReadStatus(
        weekNumber: Int,
        dayNumber: Int,
        passageIndex: Int,
        isRead: Boolean,
    )
}
