package com.quare.bibleplanner.feature.day.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import bibleplanner.feature.day.generated.resources.Res
import bibleplanner.feature.day.generated.resources.back
import bibleplanner.feature.day.generated.resources.day_week_title
import bibleplanner.feature.day.generated.resources.loading
import bibleplanner.feature.day.generated.resources.passages_completed
import com.quare.bibleplanner.core.model.book.BookDataModel
import com.quare.bibleplanner.core.model.plan.ChapterPlanModel
import com.quare.bibleplanner.core.model.plan.PassagePlanModel
import com.quare.bibleplanner.feature.day.presentation.model.DayUiState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DayTopBar(
    uiState: DayUiState,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back),
                    )
                }
                when (uiState) {
                    is DayUiState.Loaded -> {
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(
                                text = stringResource(
                                    Res.string.day_week_title,
                                    uiState.day.number,
                                    uiState.weekNumber,
                                ),
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            val (completedCount, totalCount) = calculateChapterCounts(
                                passages = uiState.day.passages,
                                books = uiState.books,
                            )
                            Text(
                                text = stringResource(
                                    Res.string.passages_completed,
                                    completedCount,
                                    totalCount,
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }

                    is DayUiState.Loading -> {
                        Text(
                            text = stringResource(Res.string.loading),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }
                }
            }
        },
    )
}

/**
 * Calculate the total number of chapters/items displayed and how many are completed.
 * Returns a Pair of (completedCount, totalCount).
 */
private fun calculateChapterCounts(
    passages: List<PassagePlanModel>,
    books: List<BookDataModel>,
): Pair<Int, Int> {
    var totalCount = 0
    var completedCount = 0

    passages.forEach { passage ->
        if (passage.chapters.isEmpty()) {
            // If no chapters specified, count as 1 item (the whole book)
            totalCount++
            if (passage.isRead) {
                completedCount++
            }
        } else {
            // Count each chapter as a separate item
            passage.chapters.forEach { chapter ->
                totalCount++
                val isChapterRead = isChapterReadForCount(
                    passage = passage,
                    chapter = chapter,
                    books = books,
                )
                if (isChapterRead) {
                    completedCount++
                }
            }
        }
    }

    return Pair(completedCount, totalCount)
}

/**
 * Check if a specific chapter within a passage is read by checking the book data.
 */
private fun isChapterReadForCount(
    passage: PassagePlanModel,
    chapter: ChapterPlanModel,
    books: List<BookDataModel>,
): Boolean {
    val book = books.find { it.id == passage.bookId } ?: return false
    val bookChapter = book.chapters.find { it.number == chapter.number } ?: return false

    val startVerse = chapter.startVerse
    val endVerse = chapter.endVerse

    return when {
        // If verse range is specified, check those specific verses
        startVerse != null && endVerse != null -> {
            val requiredVerses = startVerse..endVerse
            requiredVerses.all { verseNumber ->
                bookChapter.verses.find { it.number == verseNumber }?.isRead == true
            }
        }

        // If only start verse is specified, check from that verse to end of chapter
        startVerse != null -> {
            bookChapter.verses
                .filter { it.number >= startVerse }
                .all { it.isRead }
        }

        // If no verse range specified, check if entire chapter is read
        else -> {
            bookChapter.isRead
        }
    }
}
