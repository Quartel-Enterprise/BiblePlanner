package com.quare.bibleplanner.core.provider.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.quare.bibleplanner.core.provider.room.entity.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters WHERE bookId = :bookId ORDER BY number")
    fun getChaptersByBookId(bookId: String): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE bookId = :bookId ORDER BY number")
    suspend fun getChaptersByBookIdSuspend(bookId: String): List<ChapterEntity>

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    fun getChapterById(chapterId: Long): Flow<ChapterEntity?>

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    suspend fun getChapterByIdSuspend(chapterId: Long): ChapterEntity?

    @Query("SELECT * FROM chapters WHERE bookId = :bookId AND number = :chapterNumber")
    suspend fun getChapterByBookIdAndNumber(
        bookId: String,
        chapterNumber: Int,
    ): ChapterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: ChapterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>): List<Long>

    @Update
    suspend fun updateChapter(chapter: ChapterEntity)

    @Query("UPDATE chapters SET isRead = :isRead WHERE id = :chapterId")
    suspend fun updateChapterReadStatus(
        chapterId: Long,
        isRead: Boolean,
    )

    @Query("DELETE FROM chapters WHERE id = :chapterId")
    suspend fun deleteChapter(chapterId: Long)

    @Query("DELETE FROM chapters WHERE bookId = :bookId")
    suspend fun deleteChaptersByBookId(bookId: String)

    @Query("DELETE FROM chapters")
    suspend fun deleteAllChapters()
}
