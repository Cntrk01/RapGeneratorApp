package com.okation.aivideocreator.ui.rapper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.okation.aivideocreator.models.rapmodel.RapModelItem
import com.okation.aivideocreator.models.rappermodel.VoiceModel
import com.okation.aivideocreator.network.UberDuckApi
import javax.inject.Inject

class RapperRepository @Inject constructor(private val api : UberDuckApi) {
    private val _voices = MutableLiveData<List<VoiceModel>>()
    val voices: LiveData<List<VoiceModel>> get() = _voices
    private val _rapperVoice = MutableLiveData<ArrayList<RapModelItem>>()
    val rapperVoice: LiveData<ArrayList<RapModelItem>> get() = _rapperVoice

    suspend fun getVoices()  {
        val comeData=api.getVoiceData()
        if (comeData.isSuccessful){
            val x=comeData.body()
            x?.let {
                _voices.postValue(it)
            }
        }
    }

    suspend fun getRapperVoice(voice:String)  {
        val rapperVoice=api.getVoiceSamples(voice)
        if (rapperVoice.isSuccessful){
            val body=rapperVoice.body()
            if (body != null) { // Body boş değilse ve elemanları varsa
                _rapperVoice.postValue(body!!)
            }
        }
    }
}