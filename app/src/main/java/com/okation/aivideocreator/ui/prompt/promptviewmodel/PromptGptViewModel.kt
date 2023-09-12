package com.okation.aivideocreator.ui.prompt.promptviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.ui.prompt.promtrepository.PromptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromptGptViewModel @Inject constructor(private val repo: PromptRepository) :
    ViewModel() {

    val apiMessage: MutableLiveData<String> = repo.apiMessage
    val apiMessageTitle: LiveData<String> = repo.apiMessageTitle
    val listenCounter: LiveData<Int> = repo.listenCounter


    fun callApi(question:String)=viewModelScope.launch {
        repo.callApi(question)
    }

    fun startCounter()=viewModelScope.launch {
        repo.startCounter()
    }
    fun stopCounter()=viewModelScope.launch {
        repo.stopCounter()
    }

}