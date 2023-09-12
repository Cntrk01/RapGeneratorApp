package com.okation.aivideocreator.network


import com.okation.aivideocreator.models.beatsmodel.UberDuckApiResponse
import com.okation.aivideocreator.models.freestylemodel.Freestyle
import com.okation.aivideocreator.models.freestylemodel.TtsRequest
import com.okation.aivideocreator.models.rapmodel.RapModelItem
import com.okation.aivideocreator.models.rappermodel.VoiceModel
import retrofit2.Response
import retrofit2.http.*

interface UberDuckApi {

    @GET("reference-audio/backing-tracks")
    suspend fun getBackingTracks(@Query("detailed")detailed:Boolean=true): Response<UberDuckApiResponse>

    @Headers(
        "accept: application/json",
        "content-type: application/json",
        "authorization: Basic cHViX3Z3Ym14bWNkd2N4cGVmYXd1eDpwa19kZTYyNTU0NS05OGQ0LTQzZTQtODI2Ny1kNGI2M2JlY2RlZmQ="
    )
    @POST("tts/freestyle")
    suspend fun postTextToSpeech(@Body request: TtsRequest): Response<Freestyle>

    @GET("/voices?mode=tts-basic&slim=false")
    suspend fun getVoiceData(): Response<List<VoiceModel>>


    @GET("voices/{voicemodel_uuid}/samples")
    suspend fun getVoiceSamples(@Path("voicemodel_uuid") voicemodelUuid: String): Response<ArrayList<RapModelItem>>

}