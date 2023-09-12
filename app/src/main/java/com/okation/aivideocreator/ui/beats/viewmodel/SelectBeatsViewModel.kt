package com.okation.aivideocreator.ui.beats.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.models.beatsmodel.UberDuckApiResponse
import com.okation.aivideocreator.ui.beats.repository.SelectBeatsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectBeatsViewModel @Inject constructor(private val repo: SelectBeatsRepo) : ViewModel(){


    val beats: LiveData<UberDuckApiResponse> get() = repo.beats

    init {
        getBeats()
    }

    private fun getBeats() = viewModelScope.launch {
        repo.getBeats()
    }



}