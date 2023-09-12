package com.okation.aivideocreator.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.models.homesongmodel.SongModel
import com.okation.aivideocreator.ui.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {


    fun getSongs()=repository.getSongs()

    fun deleteSongId(deleteSong:SongModel) = viewModelScope.launch {
        repository.deleteSongId(deleteSong)
    }
    fun insertSong(songModel: SongModel)=viewModelScope.launch{
        repository.insertSong(songModel)
    }
    fun updateSong(songModel: SongModel)=viewModelScope.launch {
        repository.updateSong(songModel)
    }
}