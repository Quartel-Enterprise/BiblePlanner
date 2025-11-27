package com.quare.bibleplanner.core.books.domain

import com.quare.bibleplanner.core.model.book.BookDataModel
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooksFlow(): Flow<List<BookDataModel>>
}
