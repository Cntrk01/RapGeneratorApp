package com.okation.aivideocreator.ui.generating

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.okation.aivideocreator.databinding.FragmentGeneratingBinding
import com.okation.aivideocreator.models.freestylemodel.TtsRequest
import com.okation.aivideocreator.ui.generating.viewmodel.GeneratingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratingFragment : Fragment() {
    private var _binding : FragmentGeneratingBinding?=null
    private val binding get() = _binding!!
    private val args : GeneratingFragmentArgs by navArgs()
    private val viewModel : GeneratingViewModel by viewModels()
    private val wordList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentGeneratingBinding.inflate(inflater,container,false)
        Log.e("lyricText",args.lyricText)
        Log.e("lyricTitle",args.lyricTitle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextDataFromArgs()
        removeSlash()
        observeMethod()
    }
    private fun setTextDataFromArgs(){
        with(binding){
            rapperName.text=args.rapperName
            songTitle.text=args.lyricTitle.replace("\"", "")
            rapperImage.setImageResource(args.rapperPhoto)
        }
    }
    private fun removeSlash(){
        val lyricText = args.lyricText.replace("\"", "")
        var currentWord = ""

        for (char in lyricText) {
            if (char.isLetterOrDigit()) {
                currentWord += char
            } else {
                if (currentWord.isNotEmpty()) {
                    wordList.add(currentWord)
                    currentWord = ""
                }
            }
        }
        if (currentWord.isNotEmpty()) {
            wordList.add(currentWord)
        }
    }
    private fun observeMethod(){
        viewModel.getMusicUrl(TtsRequest(listOf(wordList),args.bpm,args.beatsUid,"zwf","json"))
        viewModel.generatedSong.observe(viewLifecycleOwner){
            if (it.mix_url !=""){
                val action=GeneratingFragmentDirections.actionGeneratingFragmentToSongFragment(it.mix_url,args.lyricTitle,args.rapperName,args.rapperPhoto,0)
                findNavController().navigate(action)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}