package com.quare.bibleplanner.feature.readingplan.presentation.model

import com.quare.bibleplanner.core.model.plan.ReadingPlanType
import org.jetbrains.compose.resources.StringResource

internal data class ReadingPlanPresentationModel(
    val name: StringResource,
    val type: ReadingPlanType,
)
