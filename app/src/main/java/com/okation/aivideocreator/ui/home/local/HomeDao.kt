package com.okation.aivideocreator.ui.home.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.okation.aivideocreator.models.homesongmodel.SongModel

@Dao
interface HomeDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE) // Eğer aynı kullanıcı adı varsa, üzerine yaz
    suspend fun insertSong(songModel: SongModel)

    @Query("SELECT * FROM songs")
    fun getAllUsers(): LiveData<List<SongModel>>

    @Query("DELETE FROM songs WHERE songs.id=:id")
    suspend fun deleteSongId(id:Int)

    @Update
    suspend fun updateSong(song: SongModel)


}