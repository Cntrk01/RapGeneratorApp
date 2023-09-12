package com.okation.aivideocreator.models.beatsmodel

data class BackingTrack(
    val bpm: Int?,
    val uuid: String?,
    val name: String?,
    val source: String?,
    val bucket: String?,
    val path: String?,
    val is_public: Boolean?,
    val verses: List<Verse>?,
    val url:String?
)