package com.quare.bibleplanner.feature.day.domain.usecase

internal data class DayUseCases(
    val updateDayReadStatus: UpdateDayReadStatusUseCase,
    val updateDayReadTimestamp: UpdateDayReadTimestampUseCase,
    val toggleChapterReadStatus: ToggleChapterReadStatusUseCase,
)
