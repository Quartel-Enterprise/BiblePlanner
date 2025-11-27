package com.quare.bibleplanner.core.provider.room.db

import androidx.room.Room
import androidx.room.RoomDatabase
import com.quare.bibleplanner.core.provider.room.utils.DatabaseUtils
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DatabaseUtils.PATH)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}
