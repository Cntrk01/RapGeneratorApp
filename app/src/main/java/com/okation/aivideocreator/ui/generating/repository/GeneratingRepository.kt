package com.okation.aivideocreator.ui.generating.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.okation.aivideocreator.models.freestylemodel.Freestyle
import com.okation.aivideocreator.models.freestylemodel.TtsRequest
import com.okation.aivideocreator.network.UberDuckApi
import javax.inject.Inject
class GeneratingRepository @Inject constructor(private val api: UberDuckApi) {
    private val _generatedSong = MutableLiveData<Freestyle>()
    val generatedSong: LiveData<Freestyle> get() = _generatedSong
    suspend fun setGenerateData(request: TtsRequest){
        val apiData=api.postTextToSpeech(request)
        if (apiData.isSuccessful){
            _generatedSong.postValue(apiData.body()!!)
        }else{
            _generatedSong.postValue(Freestyle(emptyList(),"","","","",""))
        }
    }

}