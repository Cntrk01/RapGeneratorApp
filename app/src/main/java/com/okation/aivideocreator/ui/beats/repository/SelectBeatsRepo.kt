package com.okation.aivideocreator.ui.beats.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.okation.aivideocreator.models.beatsmodel.UberDuckApiResponse
import com.okation.aivideocreator.network.UberDuckApi
import javax.inject.Inject

class SelectBeatsRepo @Inject constructor(private val api:UberDuckApi) {

    private val _beats = MutableLiveData<UberDuckApiResponse>()

    val beats: LiveData<UberDuckApiResponse> get() = _beats

    suspend fun getBeats() {
        try {
            val beat = api.getBackingTracks()
            if (beat.isSuccessful) {
                _beats.postValue(beat.body())
            }
        }catch (e:java.lang.Exception){
            println(e.printStackTrace())
        }
    }

}