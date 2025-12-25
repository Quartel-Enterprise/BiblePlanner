package com.quare.bibleplanner.feature.readingplan.presentation.component.week.day

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.quare.bibleplanner.core.books.util.getBookName
import com.quare.bibleplanner.core.model.plan.DayModel
import com.quare.bibleplanner.core.model.plan.PassagePlanModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun PassageItemTexts(
    modifier: Modifier = Modifier,
    day: DayModel,
) {
    val passageColor by animateColorAsState(
        targetValue = if (day.isRead) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        label = "dayPassageColor",
    )
    Column(modifier = modifier) {
        Text(
            text = day.passages.toDayReadingFormat(),
            style = MaterialTheme.typography.bodyLarge,
            color = passageColor,
            textDecoration = if (day.isRead) {
                TextDecoration.LineThrough
            } else {
                TextDecoration.None
            },
        )
    }
}

@Composable
private fun List<PassagePlanModel>.toDayReadingFormat(): String {
    val passagesPresentation = map { passage ->
        passage.run {
            val bookName = passage.bookId.getBookName()
            // If no chapters, show just the book name (e.g., "Obadiah")
            // Otherwise show book name with chapter ranges (e.g., "2 Samuel 5:1-10")
            if (chapterRanges.orEmpty().isEmpty()) {
                bookName
            } else {
                "$bookName $chapterRanges"
            }
        }
    }
    return passagesPresentation.joinToString(", ")
}
