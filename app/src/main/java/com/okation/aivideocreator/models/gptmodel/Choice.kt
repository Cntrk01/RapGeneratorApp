package com.okation.aivideocreator.models.gptmodel

data class Choice(
    val text: String,
    val index: Int,
    val logprobs: Any?,
    val finish_reason: String
)
