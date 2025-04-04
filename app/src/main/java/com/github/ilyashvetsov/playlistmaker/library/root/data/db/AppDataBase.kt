package com.github.ilyashvetsov.playlistmaker.library.root.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.entity.FavoriteTrackEntity
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.FavoriteDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.PlaylistsDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.TracksDao
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.PlaylistEntity
import com.github.ilyashvetsov.playlistmaker.library.playlists.data.db.entity.TrackEntity

@Database(
    version = 1,
    entities = [FavoriteTrackEntity::class, PlaylistEntity::class, TrackEntity::class]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    abstract fun getPlaylistsDao(): PlaylistsDao

    abstract fun getTracksDao(): TracksDao
}
