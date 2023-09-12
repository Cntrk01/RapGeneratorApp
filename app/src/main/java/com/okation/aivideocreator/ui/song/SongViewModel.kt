package com.okation.aivideocreator.ui.song

import android.media.MediaPlayer
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.databinding.FragmentSongBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SongViewModel : ViewModel() {

    private var mediaPlayerJob: Job? = null

    fun startUpdatingSeekBar(mediaPlayer:MediaPlayer,seekBar: SeekBar,binding:FragmentSongBinding?) {
        mediaPlayerJob?.cancel() // Önceki işlemi iptal et (eğer varsa)
        mediaPlayerJob = viewModelScope.launch {
            while (true) {
                if (mediaPlayer.isPlaying) {
                    val currentPosition = mediaPlayer.currentPosition
                    seekBar.progress = currentPosition
                    binding?.passTime?.text = formatDuration(currentPosition)
                }else{
                    mediaPlayerJob=null
                }
                delay(1000) // 1 saniyede bir güncelleme yap
            }
        }
    }

     fun formatDuration(duration: Int): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // ViewModel kapatıldığında işlemi iptal et
    override fun onCleared() {
        super.onCleared()
        mediaPlayerJob?.cancel()
    }

}