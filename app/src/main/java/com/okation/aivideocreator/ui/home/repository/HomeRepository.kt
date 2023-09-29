package com.okation.aivideocreator.ui.home.repository

import androidx.lifecycle.LiveData
import com.okation.aivideocreator.models.homesongmodel.SongModel
import com.okation.aivideocreator.ui.home.local.HomeDao
import javax.inject.Inject

class HomeRepository @Inject constructor(private val dao: HomeDao) {
    fun getSongs() : LiveData<List<SongModel>> {
        return dao.getAllUsers()
    }
    suspend fun insertSong(song: SongModel) {
        dao.insertSong(song)
    }
    suspend fun updateSong(song: SongModel) {
        dao.updateSong(song)
    }
    suspend fun deleteSongId(song: SongModel) {
        dao.deleteSongId(song.id!!)

    }
}