package com.quare.bibleplanner.core.provider.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChapterWithVerses(
    @Embedded
    val chapter: ChapterEntity,
    @Relation(
        entity = VerseEntity::class,
        parentColumn = "id",
        entityColumn = "chapterId",
    )
    val verses: List<VerseEntity>,
)
