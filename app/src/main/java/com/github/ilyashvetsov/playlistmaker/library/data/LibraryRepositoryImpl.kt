package com.github.ilyashvetsov.playlistmaker.library.data

import com.github.ilyashvetsov.playlistmaker.library.data.db.AppDatabase
import com.github.ilyashvetsov.playlistmaker.library.data.db.LibraryDao
import com.github.ilyashvetsov.playlistmaker.library.domain.LibraryRepository
import com.github.ilyashvetsov.playlistmaker.track.domain.model.Track

class LibraryRepositoryImpl(appDatabase: AppDatabase): LibraryRepository {
    private val dao: LibraryDao = appDatabase.getLibraryDao()

    override fun addTrack(track: Track) {
        dao.insertTrack(trackEntity = track.toEntity())
    }

    override fun removeTrack(track: Track) {
        dao.deleteTrack(trackEntity = track.toEntity())
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
