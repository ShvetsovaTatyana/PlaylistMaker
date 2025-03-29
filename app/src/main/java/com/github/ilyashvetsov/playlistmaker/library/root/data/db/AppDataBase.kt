package com.github.ilyashvetsov.playlistmaker.library.root.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.entity.TrackEntity
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.FavoriteDao

@Database(
    version = 1,
    entities = [TrackEntity::class]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLibraryDao(): FavoriteDao
}
