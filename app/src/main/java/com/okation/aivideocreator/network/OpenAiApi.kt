package com.okation.aivideocreator.network

import com.okation.aivideocreator.models.gptmodel.CompletionRequest
import com.okation.aivideocreator.models.gptmodel.CompletionResponse
import retrofit2.Response
import retrofit2.http.*

interface OpenAiApi {

    @Headers("Authorization: Bearer ${"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"}")
    @POST("v1/completions")
    suspend fun getEditedText(@Body compRequest: CompletionRequest): Response<CompletionResponse>





    // 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
}