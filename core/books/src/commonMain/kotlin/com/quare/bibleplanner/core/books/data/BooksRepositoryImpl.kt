package com.quare.bibleplanner.core.books.data

import com.quare.bibleplanner.core.books.domain.BooksRepository
import com.quare.bibleplanner.core.model.book.BookChapterModel
import com.quare.bibleplanner.core.model.book.BookDataModel
import com.quare.bibleplanner.core.model.book.BookId
import com.quare.bibleplanner.core.model.book.VerseModel
import com.quare.bibleplanner.core.provider.room.dao.BookDao
import com.quare.bibleplanner.core.provider.room.entity.BookWithChapters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BooksRepositoryImpl(private val dao: BookDao) : BooksRepository {
    override fun getBooksFlow(): Flow<List<BookDataModel>> = dao
        .getAllBooksWithChapters()
        .map { bookWithChapters: List<BookWithChapters> ->
            bookWithChapters.map { it.toModel() }
        }

    private fun BookWithChapters.toModel(): BookDataModel {
        val chaptersModel = chapters.map {
            val versesModel = it.verses.map { verseModel ->
                VerseModel(
                    number = verseModel.number,
                    isRead = verseModel.isRead
                )
            }
            BookChapterModel(
                number = it.chapter.number,
                verses = versesModel,
                isRead = it.chapter.isRead,
            )
        }
        return BookDataModel(
            id = BookId.valueOf(book.id),
            chapters = chaptersModel,
            isRead = book.isRead
        )
    }
}
