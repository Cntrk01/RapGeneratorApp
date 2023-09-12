package com.okation.aivideocreator.models.homesongmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?=null,
    val songUrl:String,
    val songImage:Int,
    val songName:String,
    val rapperName:String
)
