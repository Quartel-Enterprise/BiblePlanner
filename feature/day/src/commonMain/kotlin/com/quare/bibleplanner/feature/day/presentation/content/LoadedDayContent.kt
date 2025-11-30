package com.quare.bibleplanner.feature.day.presentation.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.quare.bibleplanner.feature.day.presentation.component.DayReadSection
import com.quare.bibleplanner.feature.day.presentation.component.passageList
import com.quare.bibleplanner.feature.day.presentation.model.DayUiEvent
import com.quare.bibleplanner.feature.day.presentation.model.DayUiState

@Composable
internal fun LoadedDayContent(
    modifier: Modifier = Modifier,
    maxContentWidth: Dp,
    uiState: DayUiState.Loaded,
    onEvent: (DayUiEvent) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(modifier = Modifier.width(maxContentWidth)) {
            LazyColumn {

                passageList(
                    passages = uiState.day.passages,
                    books = uiState.books,
                    onChapterToggle = { passageIndex, chapterIndex ->
                        onEvent(DayUiEvent.OnChapterToggle(passageIndex, chapterIndex))
                    },
                )

                item {
                    DayReadSection(
                        isRead = uiState.day.isRead,
                        formattedReadDate = uiState.formattedReadDate,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}
