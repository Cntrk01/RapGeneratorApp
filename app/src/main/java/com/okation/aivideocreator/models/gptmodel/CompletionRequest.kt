package com.okation.aivideocreator.models.gptmodel

data class CompletionRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int,
    val temperature: Float=0F,
)