package com.okation.aivideocreator.models.freestylemodel

data class Freestyle(
    val lines: List<Line>,
    val mix_url: String,
    val render_uuid: String,
    val render_video_response: Any,
    val title: String,
    val vocals_url: String
)