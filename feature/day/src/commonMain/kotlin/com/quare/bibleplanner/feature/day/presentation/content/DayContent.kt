package com.quare.bibleplanner.feature.day.presentation.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.quare.bibleplanner.feature.day.presentation.model.DayUiEvent
import com.quare.bibleplanner.feature.day.presentation.model.DayUiState

@Composable
internal fun DayContent(
    uiState: DayUiState,
    onEvent: (DayUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    maxContentWidth: Dp,
) {
    when (uiState) {
        is DayUiState.Loading -> LoadingDayContent(modifier)

        is DayUiState.Loaded -> LoadedDayContent(
            uiState = uiState,
            onEvent = onEvent,
            maxContentWidth = maxContentWidth,
        )
    }
}
