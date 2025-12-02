package com.quare.bibleplanner.feature.day.presentation.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bibleplanner.feature.day.generated.resources.Res
import bibleplanner.feature.day.generated.resources.mark_as_read
import bibleplanner.feature.day.generated.resources.mark_as_unread
import com.quare.bibleplanner.feature.day.presentation.component.DayReadSection
import com.quare.bibleplanner.feature.day.presentation.component.passageList
import com.quare.bibleplanner.feature.day.presentation.model.DayUiEvent
import com.quare.bibleplanner.feature.day.presentation.model.DayUiState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoadedDayContent(
    modifier: Modifier = Modifier,
    maxContentWidth: Dp,
    uiState: DayUiState.Loaded,
    onEvent: (DayUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Box(modifier = Modifier.width(maxContentWidth)) {
                    OutlinedButton(
                        onClick = {
                            onEvent(
                                DayUiEvent.OnDayReadToggle(!uiState.day.isRead),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = stringResource(
                                if (uiState.day.isRead) Res.string.mark_as_unread else Res.string.mark_as_read,
                            ),
                        )
                    }
                }
            }
        }

        passageList(
            passages = uiState.day.passages,
            chapterReadStatus = uiState.chapterReadStatus,
            onChapterToggle = { passageIndex, chapterIndex ->
                onEvent(DayUiEvent.OnChapterToggle(passageIndex, chapterIndex))
            },
            maxContentWidth = maxContentWidth,
        )

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Box(modifier = Modifier.width(maxContentWidth)) {
                    DayReadSection(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        isRead = uiState.day.isRead,
                        formattedReadDate = uiState.formattedReadDate,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}
