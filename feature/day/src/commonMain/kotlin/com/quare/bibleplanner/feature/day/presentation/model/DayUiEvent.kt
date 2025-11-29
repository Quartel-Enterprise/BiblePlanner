package com.quare.bibleplanner.feature.day.presentation.model

internal sealed interface DayUiEvent {
    data class OnChapterToggle(
        val passageIndex: Int,
        val chapterIndex: Int,
    ) : DayUiEvent

    data class OnDayReadToggle(
        val isRead: Boolean,
    ) : DayUiEvent

    data class OnEditReadDate(
        val timestamp: Long,
    ) : DayUiEvent // Epoch milliseconds

    data object OnBackClick : DayUiEvent
}
