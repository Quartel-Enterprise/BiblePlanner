package com.quare.bibleplanner.core.provider.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "verses",
    foreignKeys = [
        ForeignKey(
            entity = ChapterEntity::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["chapterId"])],
)
data class VerseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: Int,
    val chapterId: Long,
    val isRead: Boolean = false,
)
