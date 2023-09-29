package com.okation.aivideocreator.ui.rapper.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.models.rapmodel.RapModelItem
import com.okation.aivideocreator.ui.rapper.repository.RapperRepository
import com.okation.aivideocreator.models.rappermodel.VoiceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RapperViewModel @Inject constructor(private val repo:RapperRepository) : ViewModel() {
    val voices: LiveData<List<VoiceModel>> = repo.voices
    val rapperVoice: LiveData<ArrayList<RapModelItem>> = repo.rapperVoice

    fun getVoices()=viewModelScope.launch{
        try {
            repo.getVoices()
           // _voices.value=repo.voices.value null veri gelse bile çökme olmuyor ondan postvalue kullanmıyorum
        }catch (e:java.lang.Exception){
            Log.e("RapperViewModel",e.message.toString())
        }
    }
    fun getRapperVoice(voice:String)=viewModelScope.launch{
        try {
            repo.getRapperVoice(voice)
            //rapperVoice.value=repo.rapperVoice.value yaptığımda çökme olmuyor
        }catch (e:java.lang.Exception){
            Log.e("RapperViewModel",e.message.toString())
        }
    }
}