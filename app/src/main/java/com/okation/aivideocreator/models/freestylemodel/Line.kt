package com.okation.aivideocreator.models.freestylemodel

data class Line(
    val end: Int,
    val start: Int,
    val text: String,
    val words: List<Word>
)