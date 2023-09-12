package com.okation.aivideocreator.ui.home.local

import androidx.room.RoomDatabase
import com.okation.aivideocreator.models.homesongmodel.SongModel

@androidx.room.Database(entities = [SongModel::class], version = 1)
abstract class Database  : RoomDatabase() {
    abstract fun songDao(): HomeDao
}