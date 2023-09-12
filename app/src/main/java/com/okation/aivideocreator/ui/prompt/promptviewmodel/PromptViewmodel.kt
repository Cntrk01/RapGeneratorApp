package com.okation.aivideocreator.ui.prompt.promptviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PromptViewmodel : ViewModel() {

    var getText=MutableLiveData<String>()

    fun setFunText(newText: String) {
        getText.postValue(newText)
    }

}