package com.github.ilyashvetsov.playlistmaker.library.favorite.data

import com.github.ilyashvetsov.playlistmaker.library.root.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.library.favorite.data.db.FavoriteDao
import com.github.ilyashvetsov.playlistmaker.library.favorite.domain.FavoriteRepository
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(appDatabase: AppDatabase): FavoriteRepository {
    private val dao: FavoriteDao = appDatabase.getFavoriteDao()

    override suspend fun addTrack(track: Track) {
        dao.insertTrack(favoriteTrackEntity = track.toFavoriteTrackEntity())
    }

    override suspend fun removeTrack(track: Track) {
        dao.deleteTrack(favoriteTrackEntity = track.toFavoriteTrackEntity())
    }

    override fun getTracks(): Flow<List<Track>> {
        return dao.getTracks().map { trackList ->
            trackList.map { trackEntity -> trackEntity.toDomain() }
        }
    }

    override suspend fun getTrackById(trackId: Int): Track? {
        return dao.getTrackById(trackId)
            .firstOrNull()
            ?.toDomain()
    }
}
