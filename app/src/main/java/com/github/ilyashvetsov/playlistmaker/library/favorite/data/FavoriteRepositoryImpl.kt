package com.github.ilyashvetsov.playlistmaker.library.favorite.data

import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.FavoriteDao
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteRepository
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

class FavoriteRepositoryImpl(appDatabase: AppDatabase): FavoriteRepository {
    private val dao: FavoriteDao = appDatabase.getFavoriteDao()

    override fun addTrack(track: Track) {
        dao.insertTrack(favoriteTrackEntity = track.toFavoriteTrackEntity())
    }

    override fun removeTrack(track: Track) {
        dao.deleteTrack(favoriteTrackEntity = track.toFavoriteTrackEntity())
    }

    override fun getTracks(): List<Track> {
        return dao.getTracks().map { trackEntity -> trackEntity.toDomain() }
    }

    override fun getTrackById(trackId: Int): Track? {
        return dao.getTrackById(trackId)
            .firstOrNull()
            ?.toDomain()
    }
}
