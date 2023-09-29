package com.okation.aivideocreator.ui.song

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.okation.aivideocreator.databinding.FragmentSongBinding
import com.okation.aivideocreator.models.homesongmodel.SongModel
import com.okation.aivideocreator.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class SongFragment : Fragment() {
    private var _binding : FragmentSongBinding?=null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private var playbackPosition = 0
    private lateinit var seekBar: SeekBar
    private lateinit var totalTime: TextView
    private val args : SongFragmentArgs by navArgs()
    private val viewModel: SongViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSongBinding.inflate(inflater,container,false)
        seekBar = binding.horizontalProgressBar
        totalTime = binding.totalTime
        getNavArgsData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startUpdatingSeekBar(MediaPlayer(), seekBar, binding)
        startStopMediaButton()
        buttonClickItem()
        addSongRoom()
    }
    private fun addSongRoom(){
        if (args.fragmentValue==0){
            try {
                homeViewModel.insertSong(SongModel(id = null,
                    songUrl = args.musicUrl,
                    songImage = args.rapperPhoto,
                    songName = args.lyricTitle,
                    rapperName = args.rapperName)
                )
                Toast.makeText(requireContext(),"Succesfully Saved",Toast.LENGTH_SHORT).show()
            }catch (e:IOException){
                Log.e("IOEXCEP",e.toString())
            }catch (e:java.lang.Exception){
                Log.e("EXCEPTION",e.toString())
            }
        }
    }
    private fun startStopMediaButton(){
        binding.pauseSong.setOnClickListener {
            pauseMedia()
            binding.pauseSong.visibility = View.INVISIBLE
            binding.playSong.visibility = View.VISIBLE
        }
        binding.playSong.setOnClickListener {
            if (isPlaying) {
                resumeMedia()
            } else {
                startMedia()
            }
            binding.pauseSong.visibility = View.VISIBLE
            binding.playSong.visibility = View.INVISIBLE
        }
    }
    private fun buttonClickItem(){
        with(binding){
            backButton.setOnClickListener {
                mediaPlayer.stop()
                mediaPlayer.release()
                val action=SongFragmentDirections.actionSongFragmentToHomeFragment()
                findNavController().navigate(action)
            }
            shareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, args.rapperName)
                shareIntent.putExtra(Intent.EXTRA_TEXT, args.musicUrl)
                startActivity(Intent.createChooser(shareIntent, args.rapperName))
            }
            forwardSong.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    val currentPosition = mediaPlayer.currentPosition
                    val newPosition = currentPosition + 15000 // 15 saniye ileri sarma
                    mediaPlayer.seekTo(newPosition)
                }
            }
            backSong.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    val currentPosition = mediaPlayer.currentPosition
                    val newPosition = currentPosition - 15000 // 15 saniye geri sarma
                    mediaPlayer.seekTo(newPosition)
                }
            }
            songImage.setImageResource(args.rapperPhoto)
        }
    }
    private fun getNavArgsData(){
        binding.songName.text=args.lyricTitle.replace("\"", "").trim()
        binding.songSinger.text=args.rapperName.replace("\"", "").trim()
        startMedia()
    }
    private fun startMedia() {
        Log.e("url",args.musicUrl)
        stopMedia()
        if (!isPlaying) {
            mediaPlayer = MediaPlayer()
            mediaPlayer.apply {
                try {
                    setDataSource(args.musicUrl)
                    prepareAsync()
                    setOnPreparedListener {
                        it.seekTo(playbackPosition)
                        it.start()
                        if (it.isPlaying){
                            this@SongFragment.isPlaying = true
                            playbackPosition = 0
                            val duration = it.duration
                            totalTime.text = viewModel.formatDuration(duration)
                            seekBar.max = duration

                            //null pointer atıyor.
                            viewModel.startUpdatingSeekBar(
                                mediaPlayer,
                                seekBar,
                                _binding)
                        }
                    }
                    setOnCompletionListener {
                        // Müzik tamamlandığında bu metot çağrılır
                        this@SongFragment.isPlaying = false
                        stopMedia()
                        playbackPosition = 0
                        binding.playSong.visibility = View.VISIBLE
                        binding.pauseSong.visibility = View.INVISIBLE
                        seekBar.progress = 0 // SeekBar'ı sıfırla
                        binding.passTime.text="00.00"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun pauseMedia() {
        if (isPlaying) {
            mediaPlayer.pause()
            playbackPosition = mediaPlayer.currentPosition
            isPlaying = true
        }
    }
    private fun resumeMedia() {
        if (isPlaying) {
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
            isPlaying = true
            viewModel.startUpdatingSeekBar(mediaPlayer, seekBar, binding)
        }
    }
    private fun stopMedia() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}