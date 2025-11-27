package com.quare.bibleplanner.core.provider.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithChapters(
    @Embedded
    val book: BookEntity,
    @Relation(
        entity = ChapterEntity::class,
        parentColumn = "id",
        entityColumn = "bookId",
    )
    val chapters: List<ChapterWithVerses>,
)
