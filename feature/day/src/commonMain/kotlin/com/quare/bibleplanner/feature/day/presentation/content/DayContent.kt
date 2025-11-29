package com.quare.bibleplanner.feature.day.presentation.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quare.bibleplanner.feature.day.presentation.component.DayReadSection
import com.quare.bibleplanner.feature.day.presentation.component.PassageList
import com.quare.bibleplanner.feature.day.presentation.model.DayUiEvent
import com.quare.bibleplanner.feature.day.presentation.model.DayUiState

@Composable
internal fun DayContent(
    uiState: DayUiState,
    onEvent: (DayUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is DayUiState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }

        is DayUiState.Loaded -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                PassageList(
                    passages = uiState.day.passages,
                    books = uiState.books,
                    onChapterToggle = { passageIndex, chapterIndex ->
                        onEvent(DayUiEvent.OnChapterToggle(passageIndex, chapterIndex))
                    },
                )

                DayReadSection(
                    isRead = uiState.day.isRead,
                    readTimestamp = uiState.day.readTimestamp,
                    onToggle = { isRead ->
                        onEvent(DayUiEvent.OnDayReadToggle(isRead))
                    },
                    onEditDate = { timestamp ->
                        onEvent(DayUiEvent.OnEditReadDate(timestamp))
                    },
                )
            }
        }
    }
}
