package com.quare.bibleplanner.core.provider.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.quare.bibleplanner.core.provider.room.entity.BookEntity
import com.quare.bibleplanner.core.provider.room.entity.BookWithChapters
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookById(bookId: String): Flow<BookEntity?>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookByIdSuspend(bookId: String): BookEntity?

    @Transaction
    @Query("SELECT * FROM books")
    fun getAllBooksWithChapters(): Flow<List<BookWithChapters>>

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookWithChaptersById(bookId: String): Flow<BookWithChapters?>

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookWithChaptersByIdSuspend(bookId: String): BookWithChapters?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Query("UPDATE books SET isRead = :isRead WHERE id = :bookId")
    suspend fun updateBookReadStatus(
        bookId: String,
        isRead: Boolean,
    )

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: String)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}
