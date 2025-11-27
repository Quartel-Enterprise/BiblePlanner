package com.quare.bibleplanner.core.provider.room.db

import androidx.room.RoomDatabaseConstructor

expect object DatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
