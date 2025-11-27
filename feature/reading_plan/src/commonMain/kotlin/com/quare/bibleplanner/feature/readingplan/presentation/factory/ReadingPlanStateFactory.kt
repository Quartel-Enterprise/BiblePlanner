package com.quare.bibleplanner.feature.readingplan.presentation.factory

import com.quare.bibleplanner.core.model.plan.ReadingPlanType
import com.quare.bibleplanner.feature.readingplan.presentation.model.ReadingPlanUiState

internal class ReadingPlanStateFactory {
    fun create(): ReadingPlanUiState = ReadingPlanUiState(
        selectedReadingPlan = ReadingPlanType.CHRONOLOGICAL,
        weeks = emptyList(),
        progress = 0f,
    )
}
