package com.okation.aivideocreator.models.freestylemodel

import com.google.gson.annotations.SerializedName

data class TtsRequest(
    @SerializedName("lyrics") val lyrics: List<List<String>>,
    @SerializedName("bpm") val bpm: Int,
    @SerializedName("backing_track") val backingTrack: String,
    @SerializedName("voice") val voice: String,
    @SerializedName("format") val format:String
)
