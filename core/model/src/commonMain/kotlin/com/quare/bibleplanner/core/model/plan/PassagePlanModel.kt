package com.quare.bibleplanner.core.model.plan

import com.quare.bibleplanner.core.model.book.BookId

data class PassagePlanModel(
    val bookId: BookId,
    val chapters: List<ChapterPlanModel>,
    val isRead: Boolean,
    val chapterRanges: String?,
)
