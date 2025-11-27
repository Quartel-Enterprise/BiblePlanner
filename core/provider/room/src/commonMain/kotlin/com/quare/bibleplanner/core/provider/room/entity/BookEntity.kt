package com.quare.bibleplanner.core.provider.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quare.bibleplanner.core.model.book.BookId

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: String, // BookId enum value as string
    val isRead: Boolean = false,
) {

    companion object {
        fun fromBookId(
            bookId: BookId,
            isRead: Boolean = false,
        ): BookEntity = BookEntity(
            id = bookId.name,
            isRead = isRead,
        )
    }
}
