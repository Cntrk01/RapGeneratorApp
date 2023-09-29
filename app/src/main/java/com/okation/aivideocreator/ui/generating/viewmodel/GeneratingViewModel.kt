package com.okation.aivideocreator.ui.generating.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.models.freestylemodel.Freestyle
import com.okation.aivideocreator.models.freestylemodel.TtsRequest
import com.okation.aivideocreator.ui.generating.repository.GeneratingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratingViewModel @Inject constructor(private val repo: GeneratingRepository): ViewModel() {
    val generatedSong: LiveData<Freestyle> get() = repo.generatedSong
    fun getMusicUrl(request: TtsRequest)=viewModelScope.launch {
        repo.setGenerateData(request)
    }




}