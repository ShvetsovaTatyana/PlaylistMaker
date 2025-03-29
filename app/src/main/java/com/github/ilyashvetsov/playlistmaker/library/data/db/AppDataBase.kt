package com.github.ilyashvetsov.playlistmaker.library.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.ilyashvetsov.playlistmaker.library.data.db.entity.TrackEntity

@Database(
    version = 1,
    entities = [TrackEntity::class]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLibraryDao(): LibraryDao
}
